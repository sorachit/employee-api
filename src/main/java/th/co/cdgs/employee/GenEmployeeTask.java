package th.co.cdgs.employee;

import org.jboss.logging.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class GenEmployeeTask implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(GenEmployeeTask.class.getName());
    private EntityManager entityManager;
    private long start;
    private int i;
    

    GenEmployeeTask(EntityManagerFactory entityManagerFactory, long start, int i) {
        this.entityManager = entityManagerFactory.createEntityManager();
        this.start = start;
        this.i = i;
    }

    @Override
    public void run() {
        this.entityManager.getTransaction().begin();
        Employee employee = new Employee();
        employee.setFirstName("FirstName" + i);
        employee.setLastName("LastName" + i);
        employee.setGender("M");
        this.entityManager.persist(employee);
        this.entityManager.getTransaction().commit();
        if (i % 1000 == 0 || i == 999999) {
            LOGGER.info(i + " current time : " + (System.currentTimeMillis() - start));
        }
    }


}
