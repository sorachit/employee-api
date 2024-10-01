package th.co.cdgs.employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import org.jboss.resteasy.reactive.server.multipart.FormValue;
import org.jboss.resteasy.reactive.server.multipart.MultipartFormDataInput;
import io.quarkus.narayana.jta.runtime.TransactionConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("import/employee")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class ImportEmployeeResource {

    private static final String COMMA_DELIMITER = ",";
    private static final int BATCH_SIZE = 5;
    @Inject
    EmployeeService employeeService;

    @Inject
    EntityManager entityManager;

    @POST
    @Transactional
    @TransactionConfiguration(timeout = 120000)
    public void importEmployee(MultipartFormDataInput input) throws IOException {

        Map<String, Collection<FormValue>> map = input.getValues();

        for (Entry<String, Collection<FormValue>> entry : map.entrySet()) {
            for (FormValue value : entry.getValue()) {
                value.getFileItem().getInputStream();

                try (BufferedReader br = new BufferedReader(new FileReader("book.csv"))) {
                    String line;
                    int i = 0;
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(COMMA_DELIMITER);
                        if (i > 0 && i % BATCH_SIZE == 0) {
                            entityManager.flush();
                            entityManager.clear();
                        }
                        Employee employee = new Employee();
                        employee.setFirstName(values[0]);
                        employee.setLastName(values[1]);
                        employeeService.create(employee);
                    }
                }

            }
        }


    }



}
