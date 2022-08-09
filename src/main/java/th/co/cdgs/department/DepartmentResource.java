package th.co.cdgs.department;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import th.co.cdgs.employee.Employee;
import th.co.cdgs.employee.EmployeeService;

@Path("department")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class DepartmentResource {
	@Inject
	EmployeeService employeeService;

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
    
    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(Integer id) {
    	Department entity = entityManager.getReference(Department.class, id);
    	if(!employeeService.deleteByDepartment(entity.getCode())) {
    		entityManager.remove(entity);
    	}
        return Response.ok().build();
    }


}
