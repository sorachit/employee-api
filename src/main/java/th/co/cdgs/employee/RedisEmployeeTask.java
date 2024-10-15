package th.co.cdgs.employee;

import org.jboss.logging.Logger;
import io.quarkus.redis.datasource.hash.TransactionalHashCommands;
import io.quarkus.redis.datasource.transactions.TransactionResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import th.co.cdgs.ws.ProcessSocket;

public class RedisEmployeeTask implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(RedisEmployeeTask.class.getName());
    private RedisEmployeeService redisEmployeeService;
    private long start;
    private int i;
    private int loop;

    RedisEmployeeTask(RedisEmployeeService redisEmployeeService, long start, int i, int loop) {
        this.redisEmployeeService = redisEmployeeService;
        this.start = start;
        this.i = i;
        this.loop = loop;
    }

    @Override
    public void run() {
        Employee employee = new Employee();
        employee.setId(i);
        employee.setFirstName("FirstName" + i);
        employee.setLastName("LastName" + i);
        employee.setGender("M");
        redisEmployeeService.set(employee);
        if (i % 1000 == 0) {
            LOGGER.info(i + " current time : [" + (System.currentTimeMillis() - start) + "]");
        }else if (i == (loop - 1)) {
            LOGGER.info("end time : [" + (System.currentTimeMillis() - start) + "]");
        }
    }

}
