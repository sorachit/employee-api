package org.acme.hibernate.orm;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class FruitsEndpointTest {

	@Test
	public void testListAllFruits() {
		// List all, should have all 3 fruits the database has initially:
		given().when().get("/fruits").then().statusCode(200).body(containsString("Cherry"), containsString("Apple"),
				containsString("Banana"));
	}

	@Test
	public void getListAllFruitsAmount() {
		// List all, should have all 3 fruits the database has initially:
		given().when().get("/fruits/basket").then().statusCode(200).body(containsString("Cherry"), containsString("Apple"),
				containsString("Banana"),containsString("100"));
	}

	@Test
	public void findFruitsByIdShouldBeCherry() {
		given().when().get("/fruits/1").then().statusCode(200).body(containsString("Cherry"));
	}

	@Test
	public void createFruitsShouldBeGetPear() {
		// Create the Pear:
		given().when().body("{\"name\" : \"Pear\"}").contentType("application/json").post("/fruits").then()
				.statusCode(201);

		given().when().get("/fruits/10").then().statusCode(200).body(containsString("Pear"));
	}

	@Test
	public void updateFruitsShouldBeGetPear() {
		// Create the Pear:
		given().when().body("{\"name\" : \"Pear\"}").contentType("application/json").put("/fruits/1").then()
				.statusCode(200);

		given().when().get("/fruits/1").then().statusCode(200).body(containsString("Pear"));
	}

	@Test
	public void deleteFruits() {

		// Delete the Cherry:
		given().when().delete("/fruits/1").then().statusCode(204);

		// List all, cherry should be missing now:
		given().when().get("/fruits").then().statusCode(200).body(not(containsString("Cherry")),
				containsString("Apple"), containsString("Banana"));

	}

}
