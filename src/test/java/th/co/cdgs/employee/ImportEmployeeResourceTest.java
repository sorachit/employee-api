package th.co.cdgs.employee;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import org.jboss.resteasy.reactive.server.multipart.MultipartFormDataInput;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

@QuarkusTest
class ImportEmployeeResourceTest {

        @Test
        void importEmployee() {
                long start = System.currentTimeMillis();
                File csv = new File("employee.csv");
                // Sending POST request with multipart form data
                RestAssured.given().multiPart("file", csv).when().post("/import").then()
                                .statusCode(200);
                // Print the response
                System.out.println(System.currentTimeMillis() - start);
        }



        @Test
        void gen() {
                long start = System.currentTimeMillis();
                try {
                        RestAssured.given().post("/import/gen").then().statusCode(200);
                } finally {
                        System.out.println(System.currentTimeMillis() - start);
                }
        }

        @Test
        void openCursor() {
                long start = System.currentTimeMillis();
                try {
                        RestAssured.given().post("/import/openCursor").then().statusCode(200);
                } finally {
                        System.out.println(System.currentTimeMillis() - start);
                }
        }


        @Test
        void openCursorThread() {
                long start = System.currentTimeMillis();
                try {
                        RestAssured.given().post("/import/openCursorThread").then().statusCode(200);
                } finally {
                        System.out.println(System.currentTimeMillis() - start);
                }
        }

}
