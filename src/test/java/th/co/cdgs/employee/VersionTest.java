package th.co.cdgs.employee;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import th.co.cdgs.department.Department;

@QuarkusTest
class VersionTest {


        @Test
        void updateTonyToDCShouldGetDC() throws JsonProcessingException {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
                Response response = given().when().get("/employee/1").then().extract().response();
                Employee tony = objectMapper.readValue(response.getBody().asString(),
                                Employee.class);
                assertEquals("Mavel", tony.getDepartment().getName());
                tony.setDepartment(new Department(2, "DC"));
                given().when().body(objectMapper.writeValueAsString(tony))
                                .contentType("application/json").put("/employee/1").then()
                                .statusCode(200).body(containsString("DC"));
                given().when().get("/employee/1").then().statusCode(200).body(containsString("DC"));
        }

        @Test
        void updateBruceToMavelShouldOptimisticLockException() throws JsonProcessingException {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
                Response bruceResponse =
                                given().when().get("/employee/2").then().extract().response();
                Employee bruce = objectMapper.readValue(bruceResponse.getBody().asString(),
                                Employee.class);
                bruce.setVersion(0);
                Response response = given().when().body(objectMapper.writeValueAsString(bruce))
                                .contentType("application/json").put("/employee/2").then().extract()
                                .response();
                assertEquals(500, response.statusCode());
                assertEquals("jakarta.persistence.OptimisticLockException",
                                response.body().jsonPath().getString("exceptionType"));
        }

}
