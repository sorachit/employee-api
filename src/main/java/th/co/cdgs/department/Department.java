package th.co.cdgs.department;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "department")
public class Department {

    @Id
    @SequenceGenerator(name = "departmentSequence", sequenceName = "department_id_seq",
            allocationSize = 1, initialValue = 15)
    @GeneratedValue(generator = "departmentSequence")
    private Integer code;

    @Column(length = 100)
    private String name;

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



}
