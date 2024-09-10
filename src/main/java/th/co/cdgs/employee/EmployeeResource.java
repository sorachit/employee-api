package th.co.cdgs.employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import th.co.cdgs.mapper.RegisterCustomModuleCustomizer;

import th.co.cdgs.department.Department;

@Path("employee")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class EmployeeResource {

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
    @Path("searchByNativeSql")
    public List<EmployeeBean> nativeQuery(@BeanParam EmployeeBeanParam condition) throws ParseException {
        StringBuilder sql = new StringBuilder(
                "select id, first_name,last_name,concat(first_name,' ',last_name) full_name,gender,department,register_date ");
        sql.append(" from employee ");
        sql.append(" where 1=1 ");
        if (condition.getFirstName() != null) {
            sql.append("and first_name like :firstName ");
        }
        if (condition.getLastName() != null) {
            sql.append("and last_name like :lastName ");
        }
        if (condition.getGender() != null) {
            sql.append("and gender = :gender ");
        }
        if (condition.getDepartment() != null) {
            sql.append("and department = :department ");
        }
        if (condition.getStartRegisterDate() != null) {
            sql.append("and register_date >= :startRegisterDate ");
        }
        if (condition.getEndRegisterDate() != null) {
            sql.append("and register_date <= :endRegisterDate ");
        }
        sql.append("order by id");
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setFirstResult(condition.getOffset());
        query.setMaxResults(condition.getLimit());
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
        if (condition.getStartRegisterDate() != null) {
            SimpleDateFormat dateformat = new SimpleDateFormat(RegisterCustomModuleCustomizer.OBJECT_MAPPER_DATE_FORMAT);
            query.setParameter("startRegisterDate", dateformat.parse(condition.getStartRegisterDate()));
        }
        if (condition.getEndRegisterDate() != null) {
            SimpleDateFormat dateformat = new SimpleDateFormat(RegisterCustomModuleCustomizer.OBJECT_MAPPER_DATE_FORMAT);
            query.setParameter("endRegisterDate", dateformat.parse(condition.getEndRegisterDate()));
        }
        List<EmployeeBean> list = new ArrayList<>();
        List<Object[]> objects = query.getResultList();
        
        for (Object[] object : objects) {
            EmployeeBean emp = new EmployeeBean();
            emp.setId((Integer) object[0]);
            emp.setFirstName((String) object[1]);
            emp.setLastName((String) object[2]);
            emp.setFullName((String) object[3]);
            emp.setGender(String.valueOf(object[4]));
            emp.setRegisterDate((Date) object[6]);
            list.add(emp);
        }
        return list;
    }

    @POST
    @Path("search")
    public List<Employee> search(@QueryParam(value = "limit") @DefaultValue("10") int limit,
            @QueryParam(value = "offset") @DefaultValue("0") int offset, Employee condition) {
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
            jpql.append("and e.department = :department ");
        }
        if (condition.getStartRegisterDate() != null) {
            jpql.append("and e.registerDate >= :startRegisterDate ");
        }
        if (condition.getEndRegisterDate() != null) {
            jpql.append("and e.registerDate <= :endRegisterDate ");
        }
        Query query = entityManager.createQuery(jpql.toString(), Employee.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
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
        if (condition.getStartRegisterDate() != null) {
            query.setParameter("startRegisterDate", condition.getStartRegisterDate());
        }
        if (condition.getEndRegisterDate() != null) {
            query.setParameter("endRegisterDate", condition.getEndRegisterDate());
        }
        return query.getResultList();
    }

    @POST
    @Transactional
    public Response create(Employee employee) {
        if (employee.getId() != null) {
            throw new WebApplicationException("Id was invalidly set on request.",
                    Status.BAD_REQUEST);
        }
        entityManager.persist(employee);
        return Response.status(Status.CREATED).entity(employee).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response update(Integer id, Employee employee) {
        Employee entity = entityManager.find(Employee.class, id);
        if (entity == null) {
            throw new WebApplicationException("Employee with id of " + id + " does not exist.",
                    Status.NOT_FOUND);
        }
        entity.setDepartment(employee.getDepartment());
        entity.setFirstName(employee.getFirstName());
        entity.setLastName(employee.getLastName());
        entity.setGender(employee.getGender());
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
}
