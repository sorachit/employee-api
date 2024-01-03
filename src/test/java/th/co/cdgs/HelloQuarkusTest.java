package th.co.cdgs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class HelloQuarkusTest {

        @Test
        void getHelloQuarkus() {
                given().when().get("/hello").then().statusCode(200).body(containsString("Hello Quarkus"));

        }

}
