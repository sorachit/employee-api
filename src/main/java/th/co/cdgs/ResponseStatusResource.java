package th.co.cdgs;

import java.util.Map;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("responseStatus")
@ApplicationScoped
public class ResponseStatusResource {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response created(Map<String, String> body) {
        body.put("fullName", body.get("firstName") + " " + body.get("lastName"));
        return Response.status(Status.CREATED).entity(body).build();
    }
}
