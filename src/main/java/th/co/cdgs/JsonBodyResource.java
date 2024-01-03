package th.co.cdgs;

import java.util.Map;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("jsonBody")
@ApplicationScoped

public class JsonBodyResource {


    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String json(Map<String, String> body) {
        return "Hello " + body.get("firstName") + " " + body.get("lastName");
    }
}
