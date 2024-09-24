package th.co.cdgs.department;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import th.co.cdgs.employee.Employee;

@Entity
@Table(name = "department")
public class DepartmentEmp extends BaseDepartment{

    
    @OneToMany(mappedBy="department")
    private List<Employee> employees;

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}



}
