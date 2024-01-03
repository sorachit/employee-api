package th.co.cdgs;

import java.util.Map;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("jsonResponse")
@ApplicationScoped
public class JsonResponseResource {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> json(Map<String, String> body) {
        body.put("fullName", body.get("firstName") + " " + body.get("lastName"));
        return body;
    }
}
