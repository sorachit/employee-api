package th.co.cdgs.employee;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @SequenceGenerator(name = "employeeSequence", sequenceName = "employee_id_seq",
            allocationSize = 1, initialValue = 15)
    @GeneratedValue(generator = "employeeSequence")
    private Integer id;

    @Column(name = "first_name", length = 100 )
    private String firstName;

    @Column(name = "last_name", length = 100, unique = true)
    private String lastName;

    @Column(length = 1)
    private String gender;

    @Column(length = 100)
    private String department;

    @Column(name = "register_date")
    private Date registerDate;

    @Transient
    private Date startRegisterDate;

    @Transient
    private Date endRegisterDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Date getStartRegisterDate() {
        return startRegisterDate;
    }

    public void setStartRegisterDate(Date startRegisterDate) {
        this.startRegisterDate = startRegisterDate;
    }

    public Date getEndRegisterDate() {
        return endRegisterDate;
    }

    public void setEndRegisterDate(Date endRegisterDate) {
        this.endRegisterDate = endRegisterDate;
    }

    

}
