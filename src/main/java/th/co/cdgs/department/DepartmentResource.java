package th.co.cdgs.department;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("department")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class DepartmentResource {

    @Inject
    EntityManager entityManager;

    @GET
    public List<Department> get() {
        return entityManager.createQuery("from Department", Department.class).getResultList();
    }

}
