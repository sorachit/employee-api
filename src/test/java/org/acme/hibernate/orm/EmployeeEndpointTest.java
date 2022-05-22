package org.acme.hibernate.orm;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import th.co.cdgs.department.Department;
import th.co.cdgs.employee.Employee;

@QuarkusTest
public class EmployeeEndpointTest {

        @Test
        public void getEmployeeHasTonyAndSteve() {
                given().when().get("/employee").then().statusCode(200).body(containsString("Tony"),
                                containsString("Steve"));
        }

        @Test
        public void getEmployeeByIdHasTony() {
                given().when().get("/employee/1").then().statusCode(200)
                                .body(containsString("Tony"));
        }

        @Test
        public void createThoeStatusShouldCreated() throws JsonProcessingException {
                Employee employee = new Employee();
                employee.setFirstName("Thor");
                given().when().body(new ObjectMapper().writeValueAsString(employee))
                                .contentType("application/json").post("/employee").then()
                                .statusCode(201);
        }

        @Test
        public void deleteTonyShouldFindGetStatus404() {
                given().when().delete("/employee/14").then().statusCode(200);
                given().when().get("/employee/14").then().statusCode(404);
        }



        @Test
        public void updateBruceToMavelShouldGetMavel() throws JsonProcessingException {
                Employee employee = new Employee();
                employee.setId(2);
                employee.setFirstName("Bruce");
                employee.setLastName("Wayne");
                employee.setGender("M");
                employee.setDepartment(new Department(1, "Mavel"));
                ObjectMapper objectMapper = new ObjectMapper();
                given().when().body(objectMapper.writeValueAsString(employee))
                                .contentType("application/json").put("/employee/2").then()
                                .statusCode(200).body(containsString("Mavel"));


        }


        @Test
        public void putNoIdShouldException() throws JsonProcessingException {
                Employee employee = new Employee();
                employee.setId(2);
                employee.setDepartment(new Department(1, "Mavel"));
                ObjectMapper objectMapper = new ObjectMapper();
                given().when().body(objectMapper.writeValueAsString(employee))
                                .contentType("application/json").put("/employee/100").then()
                                .statusCode(404).body(containsString("Employee with id of 100"));
        }

}
