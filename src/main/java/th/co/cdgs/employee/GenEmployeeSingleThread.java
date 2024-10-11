package th.co.cdgs.employee;

import org.jboss.logging.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import th.co.cdgs.ws.ProcessSocket;

public class GenEmployeeSingleThread implements Runnable {


    private static final Logger LOGGER = Logger.getLogger(GenEmployeeSingleThread.class.getName());

    private EntityManager entityManager;
    private ProcessSocket processSocket;
    private String username;

    GenEmployeeSingleThread(String username, EntityManagerFactory entityManagerFactory, ProcessSocket processSocket) {
        this.username = username;
        this.entityManager = entityManagerFactory.createEntityManager();
        this.processSocket = processSocket;
    }
    @Override
    public void run() {
        long start = System.currentTimeMillis();
        entityManager.getTransaction().begin();
        for (int i = 0; i < 10000; i++) {
            if (i > 0 && i % 1000 == 0) {
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
        entityManager.getTransaction().commit();
        LOGGER.info("end time : " + (System.currentTimeMillis() - start));
        processSocket.sendMessage(username , "finish gen employee time : " + (System.currentTimeMillis() - start));
    }
}
