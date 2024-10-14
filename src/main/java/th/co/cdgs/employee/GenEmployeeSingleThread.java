package th.co.cdgs.employee;

import java.util.UUID;
import org.jboss.logging.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import th.co.cdgs.process.ImportProcess;
import th.co.cdgs.process.ProcessStatus;
import th.co.cdgs.ws.ProcessSocket;

public class GenEmployeeSingleThread implements Runnable {


    private static final Logger LOGGER = Logger.getLogger(GenEmployeeSingleThread.class.getName());

    private EntityManagerFactory entityManagerFactory;
    private ProcessSocket processSocket;
    private String username;
    private long start;

    GenEmployeeSingleThread(String username, EntityManagerFactory entityManagerFactory,
            ProcessSocket processSocket) {
        this.start = System.currentTimeMillis();
        this.username = username;
        this.entityManagerFactory = entityManagerFactory;
        this.processSocket = processSocket;
    }

    @Override
    public void run() {
        ProcessStatus finalStaus = ProcessStatus.PROCESSING;
        int loop = 100000;
        try {
            status(finalStaus);
            EntityManager entityManager = this.entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            for (int i = 0; i < loop; i++) {
                if (i % 1000 == 0) {
                    entityManager.flush();
                    entityManager.clear();
                    status(ProcessStatus.PROCESSING);
                    LOGGER.info(i + " current time : " + (System.currentTimeMillis() - start));
                }
                Employee employee = new Employee();
                employee.setFirstName("FirstName" + i);
                employee.setLastName("LastName" + i);
                employee.setGender("M");
                entityManager.persist(employee);
            }
            entityManager.getTransaction().commit();
            finalStaus = ProcessStatus.SUCCESS;
        } catch (Exception e) {
            finalStaus = ProcessStatus.FAIL;
        } finally {
            status(finalStaus);
            processSocket.sendMessage(username,
                    "finish gen employee time : " + (System.currentTimeMillis() - start));
        }
    }

    private void status(ProcessStatus status) {
        EntityManager em = this.entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(new ImportProcess(this.username, status));
        em.getTransaction().commit();
    }


}
