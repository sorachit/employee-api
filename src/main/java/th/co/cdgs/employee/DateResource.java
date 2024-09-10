package th.co.cdgs.employee;

import java.util.Date;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("date")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class DateResource {

    

    @GET
    public Date get() {
        return new Date();
    }


}
