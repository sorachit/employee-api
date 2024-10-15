package th.co.cdgs.employee;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.hash.HashCommands;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
class RedisEmployeeService {
    private final HashCommands<String, String, Employee> commands;

    public RedisEmployeeService(RedisDataSource ds) {
        commands = ds.hash(Employee.class);
    }

    public void set(Employee employee) {
        commands.hset(Employee.class.getName(), String.valueOf(employee.getId()), employee);
    }

    public Employee get(Integer id) {
        return commands.hget(Employee.class.getName(), String.valueOf(id));
    }

    public int del(Integer id) {
        return commands.hdel(Employee.class.getName(), String.valueOf(id));
    }

}
