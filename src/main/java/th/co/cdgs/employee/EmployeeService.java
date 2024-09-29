package th.co.cdgs.employee;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@ApplicationScoped
public class EmployeeService {
    @Inject
    EntityManager entityManager;
    @Transactional(TxType.NOT_SUPPORTED)
    public Employee find(Integer id) {
        return entityManager.createQuery(
                " from Employee e join fetch e.department join fetch e.email where e.id = :id",
                Employee.class).setParameter("id", id).getSingleResult();
    }
}
