package th.co.cdgs.employee;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import th.co.cdgs.auditlog.AuditLog;
import th.co.cdgs.auditlog.AuditLogService;

@ApplicationScoped
public class EmployeeService {
    @Inject
    EntityManager entityManager;

    @Inject
    AuditLogService auditLogService;

    @Inject
    RunningService runningSrevice;

    @Transactional(TxType.NOT_SUPPORTED)
    public Employee find(Integer id) {
        return entityManager.createQuery(
                " from Employee e join fetch e.department join fetch e.email where e.id = :id",
                Employee.class).setParameter("id", id).getSingleResult();
    }

    @Transactional
    public Employee create(Employee employee) {
        auditLogService.logCreation(new AuditLog("Create Employee", employee.toString()));
        if (employee.getId() != null) {
            employee.setId(null);
        }
        if (employee.getEmail() != null) {
            employee.getEmail().forEach(email -> {
                email.setEmployee(employee);
            });
        }
        employee.setSeqNo(runningSrevice.next(Employee.class.getName()));
        entityManager.persist(employee);
        return employee;
    }
}
