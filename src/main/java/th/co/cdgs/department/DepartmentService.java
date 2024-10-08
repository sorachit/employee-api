package th.co.cdgs.department;

import java.util.Map;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@ApplicationScoped
public class DepartmentService {

    @Inject
    EntityManager entityManager;


    @Transactional(TxType.NOT_SUPPORTED)
    public Map<String, Department> getDepartmentMap() {
        return entityManager.createQuery("from Department", Department.class).getResultStream()
                .collect(Collectors.toMap(Department::getName, department -> department));
    }


    public Department getDepartment(String name) {
        return entityManager.createQuery("from Department where name = :name", Department.class)
        .setMaxResults(1)
        .setParameter("name", name).getResultStream().findFirst().orElse(null);
    }

}
