package th.co.cdgs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class JsonBodyTest {

        @Test
        void jsonBody() {
                String requestBody = "{\"firstName\":\"James\", \"lastName\":\"Cameron\"}";
                given().when().header("Content-type", "application/json").and().body(requestBody)
                                .post("/jsonBody").then().statusCode(200)
                                .body(containsString("Hello James Cameron"));

        }

}
