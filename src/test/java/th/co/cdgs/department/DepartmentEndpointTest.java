package th.co.cdgs.department;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import th.co.cdgs.department.Department;
import th.co.cdgs.employee.Employee;

@QuarkusTest
public class DepartmentEndpointTest {
	
	@Test
    public void deleteDepartmentAndEmployee() {
        given().when().delete("/department/2").then().statusCode(200);
        given().when().get("/employee/1").then().statusCode(200).body(containsString("Tony"));
    }
}
