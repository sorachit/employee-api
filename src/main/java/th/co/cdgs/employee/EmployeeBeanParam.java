package th.co.cdgs.employee;

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


}
