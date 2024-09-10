package th.co.cdgs.employee;

import java.time.LocalDateTime;
import java.util.Date;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;


public class EmployeeBeanParam {


    @QueryParam(value = "firstName")
    private String firstName;

    @QueryParam(value = "lastName")
    private String lastName;

    @QueryParam(value = "gender")
    private String gender;

    @QueryParam(value = "department")
    private Integer department;

    @QueryParam(value = "limit") @DefaultValue("10")
    private int limit;

    @QueryParam(value = "offset") @DefaultValue("0")
    private int offset;

    @QueryParam(value = "startRegisterDate")
    private String startRegisterDate;

    @QueryParam(value = "endRegisterDate")
    private String endRegisterDate;
    

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

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }
    

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getStartRegisterDate() {
        return startRegisterDate;
    }

    public void setStartRegisterDate(String startRegisterDate) {
        this.startRegisterDate = startRegisterDate;
    }

    public String getEndRegisterDate() {
        return endRegisterDate;
    }

    public void setEndRegisterDate(String endRegisterDate) {
        this.endRegisterDate = endRegisterDate;
    }

    


}
