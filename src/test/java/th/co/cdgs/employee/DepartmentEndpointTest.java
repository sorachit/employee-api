package th.co.cdgs.employee;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class DepartmentEndpointTest {

        @Test
        void getDepartment() {
                given().when().get("/department").then().statusCode(200).body("$", hasSize(2))
                                .body(containsString("Mavel"), containsString("DC"));
        }

        @Test
        void getDepartmentEmp() {
                given().when().get("/department/emp").then().statusCode(200).body("$", hasSize(2))
                                .body(containsString("Mavel"), containsString("DC"))
                                .body("find { it.code == 1 }.employees", hasSize(8))
                                .body("find { it.code == 1 }.employees.firstName",
                                                containsInAnyOrder("Tony", "Bruce", "Carol",
                                                                "Steve", "Natasha", "Stephen",
                                                                "Peter", "Peter"))
                                .body("find { it.code == 2 }.employees.size()", is(6))
                                .body("find { it.code == 2 }.employees.firstName",
                                                containsInAnyOrder("Bruce", "Barry",
                                                                "Diana", "Clark", "Arthur",
                                                                "Victor"));
        }

}
