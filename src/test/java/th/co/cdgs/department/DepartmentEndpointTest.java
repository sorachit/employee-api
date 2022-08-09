package th.co.cdgs.department;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class DepartmentEndpointTest {
		
	@Test
    public void deleteDepartmentAndEmployeeShouldBeFoundMavelAndTony() {
        given().when().delete("/department/2").then().statusCode(200);
        given().when().get("/department/1").then().statusCode(200).body(containsString("Mavel"));
        given().when().get("/employee/1").then().statusCode(200).body(containsString("Tony"));
    }
	
	
	@Test
    public void deleteDepartmentDCAndEmployee() {
        given().when().delete("/department/1").then().statusCode(200);
        given().when().get("/department/1").then().statusCode(404);
        given().when().get("/employee/3").then().statusCode(404);
    }
}
