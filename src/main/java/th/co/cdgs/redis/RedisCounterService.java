package th.co.cdgs.redis;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RedisCounterService {
private final ValueCommands<String, Long> commands;

    public RedisCounterService(RedisDataSource ds) {
        commands = ds.value(Long.class); 
    }

    public long get(String key) {
        Long l = commands.get(key);  
        if (l == null) {
            return 0L;
        }
        return l;
    }

    public long incr(String key) {
        return commands.incr(key);  
    }
}
