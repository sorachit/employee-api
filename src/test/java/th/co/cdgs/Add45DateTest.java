package th.co.cdgs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class Add45DateTest {

        @Test
        void add45Date() {
                String requestBody = "{\"inputDate\":\"2024-01-03T23:24:46.120+07:00\"}";
                given().when().header("Content-type", "application/json").and().body(requestBody)
                .post("/dateTime/add45Date").then().statusCode(200)
                                .body(containsString("2024-02-17T23:24:46.120+07:00"));

        }


}
