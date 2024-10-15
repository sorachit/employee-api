package th.co.cdgs.employee;

import org.jboss.logging.Logger;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.hash.HashCommands;
import io.quarkus.redis.datasource.hash.ReactiveTransactionalHashCommands;
import io.quarkus.redis.datasource.hash.TransactionalHashCommands;
import io.quarkus.redis.datasource.transactions.TransactionResult;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
class RedisTxEmployeeService {
    private static final Logger LOGGER = Logger.getLogger(RedisTxEmployeeService.class.getName());
    private static final int LOOP = 1000000;
    private static final int BATCH_SIZE = 1000;
    @Inject
    ReactiveRedisDataSource ds;

    @Inject
    RedisDataSource redisDataSource;

    public void process() {
        long start = System.currentTimeMillis();
        TransactionResult result = redisDataSource.withTransaction(tx -> {
            TransactionalHashCommands<String, String, Employee> hash = tx.hash(Employee.class);
            for (int i = 0; i < LOOP; i++) {
                Employee employee = new Employee();
                employee.setId(i);
                employee.setFirstName("FirstName" + i);
                employee.setLastName("LastName" + i);
                employee.setGender("M");
                hash.hset(Employee.class.getName(), String.valueOf(employee.getId()), employee);
                if (i % BATCH_SIZE == 0) {
                    LOGGER.info(i + " current time : " + (System.currentTimeMillis() - start));
                }
            }
        });
        
        LOGGER.info("end time : " + (System.currentTimeMillis() - start));
    }

}
