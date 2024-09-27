package th.co.cdgs.employee;

import java.util.List;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PrePersist;
import jakarta.transaction.Transactional;
import th.co.cdgs.running.RunningSeq;

@Dependent
public class EmployeeRunning {

    @Inject
    EntityManager entityManager;


    @PrePersist
    public void prePersist(Employee employee) {
        RunningSeq entity = entityManager
                .createQuery(" from RunningSeq where entityClass = :entityClass",
                        RunningSeq.class)
                    .setParameter("entityClass", Employee.class.getName())
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .setHint("javax.persistence.lock.timeout", 5000) // timeout 5 วินาที
                .getSingleResult();
        entity.incrementSequence();
        entityManager.merge(entity);
        employee.setSeqNo(entity.getCurrentSequence());
    }


}
