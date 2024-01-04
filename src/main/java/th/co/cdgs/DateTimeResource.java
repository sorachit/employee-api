package th.co.cdgs;

import java.util.Calendar;
import java.util.Date;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("dateTime")
@ApplicationScoped
public class DateTimeResource {
    
    @GET
    @Path("currentDate")
    public Date currentDate() {
        return new Date();
    }

    @POST
    @Path("add45Date")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add45Day(DateTimeRequest body) {
        Calendar c = Calendar.getInstance();
        c.setTime(body.getInputDate());
        c.add(Calendar.DATE, 45);
        return Response.ok().entity(c.getTime()).build();
    }
}
