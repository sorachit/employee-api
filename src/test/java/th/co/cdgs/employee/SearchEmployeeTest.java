package th.co.cdgs.employee;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class SearchEmployeeTest {

        @Test
        void searchEmployeeByDepartmentHasClark() {
                given().when().get("/employee/search?department=DC").then().statusCode(200)
                                .body(containsString("Clark"));
        }

        @Test
        void searchByNativeQueryGetEmployeeByDepartmentHasClark() {
                given().when().get("/employee/searchByNativeSql?department=DC").then().statusCode(200)
                                .body(containsString("Clark"));
        }

}
