package th.co.cdgs.redis;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/counter")
@Singleton
public class CounterResource {

    @Inject
    RedisCounterService redisCounterService;

    private static final String COUNTER = "counter";

    @GET
    public Long current() {
        return redisCounterService.get(COUNTER);
    }

    @POST
    public long increment() {
        return redisCounterService.incr(COUNTER);
    }

}
