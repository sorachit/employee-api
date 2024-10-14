package th.co.cdgs.employee;

import org.jboss.logging.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import th.co.cdgs.ws.ProcessSocket;

public class GenEmployeeTask implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(GenEmployeeTask.class.getName());
    private EntityManagerFactory entityManagerFactory;
    private ProcessSocket processSocket;
    private long start;
    private int i;
    private int loop;
    private String username;
    GenEmployeeTask(EntityManagerFactory entityManagerFactory,ProcessSocket processSocket , String username, long start, int i , int loop) {
        this.entityManagerFactory = entityManagerFactory;
        this.start = start;
        this.i = i;
        this.loop = loop;
        this.username = username;
        this.processSocket = processSocket;
    }
    @Override
    public void run() {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Employee employee = new Employee();
        employee.setFirstName("FirstName" + i);
        employee.setLastName("LastName" + i);
        employee.setGender("M");
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
        if (i % 1000 == 0 || i == (loop-1)) {
            LOGGER.info(i + " current time : " + (System.currentTimeMillis() - start));
            processSocket.sendMessage(username, String.valueOf((i+1) * 100 / loop));
        }
    }


}
