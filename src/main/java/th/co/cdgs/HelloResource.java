package th.co.cdgs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("hello")
@ApplicationScoped
public class HelloResource {
    @GET
    public String getHello() {
        return "Hello Quarkus";
    }
}
