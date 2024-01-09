package th.co.cdgs.employee;

import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("employee")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class EmployeeResource {
	
	@Inject
	EmployeeService employeeService;

    @Inject
    EntityManager entityManager;

    @GET
    public List<Employee> get() {
        return entityManager.createQuery("from Employee", Employee.class).getResultList();
    }

    @GET
    @Path("{id}")
    public Employee getSingle(Integer id) {
        Employee entity = entityManager.find(Employee.class, id);

        if (entity == null) {
            throw new WebApplicationException("employee with id of " + id + " does not exist.",
                    Status.NOT_FOUND);
        }
        return entity;
    }
    
    @GET
    @Path("nativeQuery")
    public List<Employee> nativeQuery(@BeanParam EmployeeBeanParam condition) {
        StringBuilder jpql = new StringBuilder("select id, first_name,last_name,gender,department from employee where 1=1 ");
        if (condition.getFirstName() != null) {
            jpql.append("and first_name like :firstName ");
        }
        if (condition.getLastName() != null) {
            jpql.append("and last_name like :lastName ");
        }
        if (condition.getGender() != null) {
            jpql.append("and gender = :gender ");
        }
        if (condition.getDepartment() != null) {
            jpql.append("and department = :department ");
        }
        Query query = entityManager.createNativeQuery(jpql.toString(), Employee.class);
        if (condition.getFirstName() != null) {
            query.setParameter("firstName", condition.getFirstName());
        }
        if (condition.getLastName() != null) {
            query.setParameter("lastName", condition.getLastName());
        }
        if (condition.getGender() != null) {
            query.setParameter("gender", condition.getGender());
        }
        if (condition.getDepartment() != null) {
            query.setParameter("department", condition.getDepartment());
        }
        return query.getResultList();
    }

    @GET
    @Path("search")
    public List<Employee> getByCondition(@BeanParam EmployeeBeanParam condition) {
        StringBuilder jpql = new StringBuilder("from Employee e where 1=1 ");
        if (condition.getFirstName() != null) {
            jpql.append("and e.firstName like :firstName ");
        }
        if (condition.getLastName() != null) {
            jpql.append("and e.lastName like :lastName ");
        }
        if (condition.getGender() != null) {
            jpql.append("and e.gender = :gender ");
        }
        if (condition.getDepartment() != null) {
            jpql.append("and e.department.code = :department ");
        }
        Query query = entityManager.createQuery(jpql.toString(), Employee.class);
        if (condition.getFirstName() != null) {
            query.setParameter("firstName", condition.getFirstName());
        }
        if (condition.getLastName() != null) {
            query.setParameter("lastName", condition.getLastName());
        }
        if (condition.getGender() != null) {
            query.setParameter("gender", condition.getGender());
        }
        if (condition.getDepartment() != null) {
            query.setParameter("department", condition.getDepartment());
        }
        return query.getResultList();
    }

    @POST
    @Transactional
    public Response create(Employee employee) {
        if (employee.getId() != null) {
            employee.setId(null);
        }
        entityManager.persist(employee);
        return Response.status(Status.CREATED).entity(employee).build();
    }

    @Transactional
    @PUT
    @Path("{id}")
    public Response update(Integer id, Employee employee) {
        // Employee entity = employeeService.findEmployee(id);
        // if (entity == null) {
        //     throw new WebApplicationException("Employee with id of " + id + " does not exist.",
        //             Status.NOT_FOUND);
        // }
        
       Employee entity = entityManager.merge(employee);
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(Integer id) {
        Employee entity = entityManager.getReference(Employee.class, id);
        if (entity == null) {
            throw new WebApplicationException("Employee with id of " + id + " does not exist.",
                    Status.NOT_FOUND);
        }
        entityManager.remove(entity);
        return Response.ok().build();
    }

    @DELETE
    @Transactional
    public Response deleteList(List<Integer> ids) {
        for (Integer id : ids) {
            Employee entity = entityManager.getReference(Employee.class, id);
            if (entity == null) {
                throw new WebApplicationException("Employee with id of " + id + " does not exist.",
                        Status.NOT_FOUND);
            }
            entityManager.remove(entity);
        }
        return Response.ok().build();
    }
}
