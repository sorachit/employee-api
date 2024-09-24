package th.co.cdgs.employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "employee_code",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"current_year", "current_sequence"})})
public class EmployeeCode {

    @Id
    @SequenceGenerator(name = "employeeCodeSequence", sequenceName = "employee_code_id_seq",
            allocationSize = 1, initialValue = 15)
    @GeneratedValue(generator = "employeeCodeSequence")
    private Integer id;
    @Column(name = "current_year", length = 4)
    private String currentYear;
    @Column(name = "current_sequence" , nullable = false)
    private int currentSequence;
    public String getEmployeeCode() {
        return currentYear + String.format("%04d", currentSequence);
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
    public int getCurrentSequence() {
        return currentSequence;
    }
    public void setCurrentSequence(int currentSequence) {
        this.currentSequence = currentSequence;
    }
    public String getCurrentYear() {
        return currentYear;
    }
    public void setCurrentYear(String currentYear) {
        this.currentYear = currentYear;
    }

}
