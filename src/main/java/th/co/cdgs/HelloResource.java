package th.co.cdgs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("hello")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class HelloResource {

    @GET
    public String getHello() {
        return "Hello Quarkus";
    }

}
