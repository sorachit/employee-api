package th.co.cdgs.employee;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import th.co.cdgs.department.Department;

@QuarkusTest
class VersionTest {



        @Test
        void updateBruceToMavelShouldGetMavel() throws JsonProcessingException {
                Employee employee = new Employee();
                employee.setId(2);
                employee.setFirstName("Bruce");
                employee.setLastName("Wayne");
                employee.setGender("M");
                employee.setVersion(0);
                employee.setDepartment(new Department(1, "Mavel"));
                ObjectMapper objectMapper = new ObjectMapper();
                Response response = given().when().body(objectMapper.writeValueAsString(employee))
                                .contentType("application/json").put("/employee/2").then().extract()
                                .response();
                assertEquals(500, response.statusCode());
                assertEquals("jakarta.persistence.OptimisticLockException",
                                response.body().jsonPath().getString("exceptionType"));


        }

}
