package th.co.cdgs.department;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import th.co.cdgs.employee.Employee;

@Entity
@Table(name = "department")
public class DepartmentEmp {

	@Id
	@Column(length = 100)
    private Integer code;

    @Column(length = 100)
    private String name;
    
    @OneToMany(mappedBy="department")
    List<Employee> employees;
    
   
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}



}
