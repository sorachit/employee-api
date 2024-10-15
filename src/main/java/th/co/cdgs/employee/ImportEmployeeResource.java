package th.co.cdgs.employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.server.multipart.FormValue;
import org.jboss.resteasy.reactive.server.multipart.MultipartFormDataInput;
import io.quarkus.narayana.jta.runtime.TransactionConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import th.co.cdgs.department.Department;
import th.co.cdgs.department.DepartmentService;
import th.co.cdgs.process.ImportProcess;
import th.co.cdgs.ws.ProcessSocket;

@Path("import")
@ApplicationScoped
public class ImportEmployeeResource {
    private static final int LOOP = 1000000;
    private static final Logger LOGGER = Logger.getLogger(ImportEmployeeResource.class.getName());
    private static final String COMMA_DELIMITER = ",";
    private static final int BATCH_SIZE = 1000;
    @Inject
    EmployeeService employeeService;

    @Inject
    DepartmentService departmentService;

    @Inject
    EntityManager entityManager;

    @Inject
    EntityManagerFactory entityManagerFactory; // ใช้ EntityManagerFactory เพื่อสร้าง EntityManager ใหม่ในแต่ละ thread

    @Inject
    ProcessSocket processSocket;

    @Inject
    RedisEmployeeService redisEmployeeService;

    @Inject
    RedisTxEmployeeService redisTxEmployeeService;
    

    @POST
    @Transactional
    public Response importEmployee(MultipartFormDataInput input) throws IOException {
        long start = System.currentTimeMillis();
        Map<String, Department> departmentMap = departmentService.getDepartmentMap();
        for (Entry<String, Collection<FormValue>> entry : input.getValues().entrySet()) {
            for (FormValue value : entry.getValue()) {
                createEmployeeFromInputStream(departmentMap, value);
            }
        }
        LOGGER.info("end time : " + (System.currentTimeMillis() - start));
        return Response.ok().build();
    }

    private void createEmployeeFromInputStream(Map<String, Department> departmentMap, FormValue value)
            throws IOException {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(value.getFileItem().getInputStream()))) {
            String line;
            boolean isFirstLine = true;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                flush(i);
                String[] values = line.split(COMMA_DELIMITER);
                Employee employee = createEmployee(departmentMap, values);
                createEmployeeEmail(values, employee);
                employeeService.create(employee);
            }
        }
    }

    private Employee createEmployee(Map<String, Department> departmentMap, String[] values) {
        Employee employee = new Employee();
        employee.setFirstName(values[0]);
        employee.setLastName(values[1]);
        employee.setGender(values[2]);
        employee.setDepartment(departmentMap.get(values[3]));
        return employee;
    }

    private void createEmployeeEmail(String[] values, Employee employee) {
        if (values.length > 4 && values[4] != null) {
            EmployeeEmail employeeEmail = new EmployeeEmail();
            employeeEmail.setEmail(values[4]);
            employeeEmail.setEmployee(employee);
            employee.addEmail(employeeEmail);
        }
    }

    private void flush(int i) {
        if (i > 0 && i % BATCH_SIZE == 0) {
            entityManager.flush();
            entityManager.clear();
        }
    }

    @POST
    @Path("/gen")
    @Transactional
    @TransactionConfiguration(timeout = 120)
    public Response gen() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < LOOP; i++) {
            if (i > 0 && i % BATCH_SIZE == 0) {
                entityManager.flush();
                entityManager.clear();
            }
            Employee employee = new Employee();
            employee.setFirstName("FirstName" + i);
            employee.setLastName("LastName" + i);
            employee.setGender("M");
            if (i % 1000 == 0) {
                LOGGER.info(i + " current time : " + (System.currentTimeMillis() - start));
            }
            entityManager.persist(employee);

        }
        LOGGER.info("end time : " + (System.currentTimeMillis() - start));
        return Response.ok().build();
    }

    @GET
    @Path("/status/{username}")
    public Response status(String username) {
        ImportProcess importProcess = entityManager.find(ImportProcess.class, username);
        return Response.ok().entity(importProcess).build();
    }

    
    @POST
    @Path("/genAsyncSignle/{username}")
    public Response genAsyncSignle(String username) {
        Thread thread = new Thread(
                new GenEmployeeSingleThread(username, entityManagerFactory, processSocket));
        thread.start();
        return Response.ok().build();
    }

    @POST
    @Path("/genAsync/{username}")
    public Response genAsync(String username) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        long start = System.currentTimeMillis();
        for (int i = 0; i < LOOP; i++) {
            executorService.submit(new GenEmployeeTask(entityManagerFactory, processSocket, username, start, i, LOOP));
        }
        return Response.ok().build();
    }
    

    @POST
    @Path("/redis")
    public Response redis() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < LOOP; i++) {
            Employee employee = new Employee();
            employee.setId(i);
            employee.setFirstName("FirstName" + i);
            employee.setLastName("LastName" + i);
            employee.setGender("M");
            redisEmployeeService.set(employee);
            if (i % BATCH_SIZE == 0) {
                LOGGER.info(i + " current time : " + (System.currentTimeMillis() - start));
            }
        }
        LOGGER.info("end time : " + (System.currentTimeMillis() - start));
        return Response.ok().build();
    }


    @POST
    @Path("/redis-tx")
    public Response redisTx() {
        redisTxEmployeeService.process();
        return Response.ok().build();
    }
    

    @POST
    @Path("/redis-async")
    public Response redisAsync() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        long start = System.currentTimeMillis();
        for (int i = 0; i < LOOP; i++) {
            executorService.submit(new RedisEmployeeTask(redisEmployeeService, start, i, LOOP));
        }
        return Response.ok().build();
    }

    private Connection getConnection() throws SQLException {
        Config config = ConfigProvider.getConfig();
        String url = config.getValue("quarkus.datasource.jdbc.url", String.class);
        String user = config.getValue("quarkus.datasource.username", String.class);
        String pass = config.getValue("quarkus.datasource.password", String.class);
        return DriverManager.getConnection(url, user, pass);
    }

    @POST
    @Path("/openCursor")
    public Response openCursor() {
        long start = System.currentTimeMillis();
        int id = 15;
        String sql = "SELECT * FROM employee";
        try (Connection connection = getConnection();
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE)) {
            ResultSet rs = statement.executeQuery(sql);
            for (int i = 0; i < 1000000; i++) {
                rs.moveToInsertRow();
                rs.updateInt("ID", id);
                rs.updateString("first_name", "FirstName" + id);
                rs.updateString("last_name", "LastName" + id);
                rs.updateString("gender", "M");
                rs.insertRow();
                if (i % 1000 == 0) {
                    LOGGER.info(i + " current time : " + (System.currentTimeMillis() - start));
                }
                id++;
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
        LOGGER.info("end time : " + (System.currentTimeMillis() - start));
        return Response.ok().build();
    }

}
