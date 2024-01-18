package th.co.cdgs.employee;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;

@QuarkusTest
class SearchNativeEmployeeTest {

        @Test
        void searchTonyStarkShouldBeGetFullName() {
                Response response =  given().when().get("/employee/searchByNativeSql?firstName=Tony&lastName=Stark")
                                .then().extract().response();
                assertEquals(200, response.getStatusCode());
                assertEquals("[Tony Stark]", response.body().jsonPath().getString("fullName"));
        }

}
