package th.co.cdgs.auditlog;

import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import th.co.cdgs.department.Department;

@ApplicationScoped
public class AuditLogService  {
	@Inject
	EntityManager entityManager;

	@Transactional(TxType.REQUIRES_NEW)
    public void logCreation(AuditLog auditLog) {
        entityManager.persist(auditLog);
    }

}
