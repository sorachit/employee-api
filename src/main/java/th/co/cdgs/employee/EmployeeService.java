package th.co.cdgs.employee;

import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import th.co.cdgs.department.Department;

@ApplicationScoped
public class EmployeeService {
	@Inject
	EntityManager entityManager;

	@Transactional(TxType.NOT_SUPPORTED)
	public List<Employee> findEmployeeByDepartment(Integer department) {
		String jpql = "from Employee e where e.department.code = :department ";
		Query query = entityManager.createQuery(jpql, Employee.class);
		query.setParameter("department", department);
		return query.getResultList();
	}

	@Transactional(TxType.REQUIRES_NEW)
	public void deleteEmployee(Integer id) {
		Employee entity = entityManager.getReference(Employee.class, id);
		entityManager.remove(entity);
	}

	@Transactional
	public void deleteDepartment(Integer id) {
		Department entity = entityManager.getReference(Department.class, id);
    	entityManager.remove(entity);
	}

}
