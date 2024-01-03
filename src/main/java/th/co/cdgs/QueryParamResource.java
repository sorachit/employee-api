package th.co.cdgs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("queryParam")
@ApplicationScoped

public class QueryParamResource {


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String queryParam(@QueryParam("firstName") String firstName,
            @QueryParam("lastName") String lastName) {
        return "Hello " + firstName + " " + lastName;
    }
}
