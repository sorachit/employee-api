package th.co.cdgs.department;

import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

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
    
    @GET
    @Path("emp")
    public List<DepartmentEmp> getDepartmentEmp() {
        return entityManager.createQuery("from DepartmentEmp", DepartmentEmp.class).getResultList();
    }
    
    
    @GET
    @Path("{id}")
    public Department getSingle(Integer id) {
    	Department entity = entityManager.find(Department.class, id);

        if (entity == null) {
            throw new WebApplicationException("employee with id of " + id + " does not exist.",
                    Status.NOT_FOUND);
        }
        return entity;
    }

}
