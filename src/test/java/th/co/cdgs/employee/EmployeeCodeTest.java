package th.co.cdgs.employee;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import th.co.cdgs.department.Department;

@QuarkusTest
class EmployeeEndpointTest {

        @Test
        void getEmployeeHasTonyAndSteve() {
                given().when().get("/employee").then().statusCode(200).body(containsString("Tony"),
                                containsString("Steve"));
        }

}
