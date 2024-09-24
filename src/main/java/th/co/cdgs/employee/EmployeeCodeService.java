package th.co.cdgs.employee;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;

@ApplicationScoped
public class EmployeeCodeService {

    @Inject
    EntityManager entityManager;


    @GET
    @Transactional
    public String getEmployeeCode() {
        Calendar calendar = Calendar.getInstance();
        String currentYear = String.valueOf(calendar.get(Calendar.YEAR));
        List<EmployeeCode> result = entityManager
                .createQuery(" from EmployeeCode WHERE currentYear = :currentYear",
                        EmployeeCode.class)
                .setParameter("currentYear", currentYear)
                // .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                // .setHint("javax.persistence.lock.timeout", 5000) // timeout 5 วินาที
                .getResultList();
        EmployeeCode entity = result.isEmpty() ? null : result.get(0);
        if (entity == null) {
            entity = new EmployeeCode();
            entity.setCurrentYear(currentYear);
            entity.setCurrentSequence(0);
        }
        entity.setCurrentSequence(entity.getCurrentSequence() + 1);
        entityManager.merge(entity);
        return entity.getEmployeeCode();
    }


}
