package th.co.cdgs.employee;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import io.quarkus.logging.Log;

@ApplicationScoped
public class EmployeeService {
	@Inject
	EmployeeService employeeService;
	@Inject
	EntityManager entityManager;

	@Transactional
	public boolean deleteByDepartment(Integer department) {
		boolean hasError = false;
		String jpql = "from Employee e where e.department.code = :department ";
		Query query = entityManager.createQuery(jpql, Employee.class);
		query.setParameter("department", department);
		List<Employee> emps = query.getResultList();
		for (Employee emp : emps) {
			try {
				employeeService.deleteById(emp.getId());
			} catch (Exception e) {
				Log.error(e);
				hasError = true;
			}
		}
		return hasError;
	}

	@Transactional(value = TxType.REQUIRES_NEW)
	public void deleteById(Integer id) {
		Employee entity = entityManager.find(Employee.class, id);
		entityManager.remove(entity);
	}

}
