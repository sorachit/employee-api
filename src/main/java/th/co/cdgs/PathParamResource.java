package th.co.cdgs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("pahtParam")
@ApplicationScoped
public class PathParamResource {
    @GET
    @Path("{name}")
    public String getHelloName(@PathParam("name") String name) {
        return "Hello " + name;
    }
}
