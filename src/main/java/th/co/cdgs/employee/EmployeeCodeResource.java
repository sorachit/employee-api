package th.co.cdgs.employee;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

@Path("code")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class EmployeeCodeResource {

    @Inject
    EmployeeCodeService employeeCodeService;

    @GET
    public String getEmployeeCode() {
        return employeeCodeService.getEmployeeCode();
    }


}
