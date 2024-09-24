package th.co.cdgs.department;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "department")
public class Department extends BaseDepartment{
    Department(){	
    }
    
    public Department(Integer code,String name){
    	super.setCode(code);
    	this.setName(name);
    }
}
