package th.co.cdgs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class PathParamTest {

        @Test
        void pahtParma() {
                given().when().get("/pahtParam/James").then().statusCode(200).body(containsString("Hello James"));

        }

}
