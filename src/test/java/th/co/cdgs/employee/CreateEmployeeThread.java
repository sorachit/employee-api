package th.co.cdgs.employee;

import static io.restassured.RestAssured.given;
import java.security.SecureRandom;
import java.util.concurrent.Callable;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

public class CreateEmployeeThread implements Callable<String> {

    private String api;

    public CreateEmployeeThread(String api) {
        this.api = api;
    }

    public static String generateName(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            result.append(characters.charAt(index));
        }

        return result.toString();
    }


    @Override
    public String call() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName(generateName(5));
        employee.setLastName(generateName(5));
        Response response = given().when().body(new ObjectMapper().writeValueAsString(employee))
                .contentType("application/json").post(api).then().extract().response();
        return response.getBody().asString();


    }

}
