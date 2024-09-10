package th.co.cdgs.employee;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import th.co.cdgs.department.Department;

@QuarkusTest
class SearchEmployeeTest {

        @Test
        void searchEmployeeByDepartmentHasClark() throws JsonProcessingException {
                Employee employee = new Employee();
                employee.setDepartment(new Department(2, "DC"));
                ObjectMapper objectMapper = new ObjectMapper();
                given().when().body(objectMapper.writeValueAsString(employee))
                .contentType("application/json").post("/employee/search").then().statusCode(200)
                                .body(containsString("Clark"));
        }

        @Test
        void searchByNativeQueryGetEmployeeByDepartmentHasClark() {
                given().when().get("/employee/searchByNativeSql?department=2").then().statusCode(200)
                                .body(containsString("Clark"));
        }

        @Test
        void searchEmployeeByDate() throws JsonProcessingException {
                Map<String, String> request = new HashMap<>();
                request.put("startRegisterDate", "2024-01-08T00:00:00.000+07:00");
                request.put("endRegisterDate", "2024-01-09T00:00:00.000+07:00");
                ObjectMapper objectMapper = new ObjectMapper();
                given().when().body(objectMapper.writeValueAsString(request)).contentType("application/json")
                                .post("/employee/search").then().statusCode(200)
                                .body(containsString("Steve"),containsString("Natasha"));
        }

}
