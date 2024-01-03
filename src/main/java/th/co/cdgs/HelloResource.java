package th.co.cdgs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("hello")
@ApplicationScoped

public class HelloResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getHello() {
        return "Hello Quarkus";
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getHelloName(@PathParam("name") String name) {
        return "Hello " + name;
    }

    @GET
    @Path("/query")
    @Produces(MediaType.TEXT_PLAIN)
    public String getHello(@QueryParam("name") String name) {
        return "Hello " + name;
    }
}
