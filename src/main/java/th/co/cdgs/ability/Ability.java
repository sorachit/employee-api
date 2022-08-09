package th.co.cdgs.ability;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import th.co.cdgs.employee.Employee;

@Entity
@Table(name = "ability")
public class Ability {

    @Id
    private Integer id;

    @Column(length = 100)
    private String power;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee")
    private Employee employee;
    
    Ability(){
    	
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
    
    



}
