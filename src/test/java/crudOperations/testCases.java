package crudOperations;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;


public class testCases {
    private static final Logger logger = LoggerFactory.getLogger(crudOperations.testCases.class);

    private int userId;
    private static final String baseURI = "https://gorest.co.in";
    private static final String bearerToken = "Bearer 5dc32152a465cc9b04dec8c4cca6e6ac9c2eaf6a0064d960cde7ab93c6caa055";

    @Test(priority = 1, description = "Verify successful user creation with valid details")
    public void createValidUser() {
        RestAssured.baseURI = baseURI;
        String requestBody = "{\"name\":\"" + randomDataGenerator.createRandomName() + "\",\"gender\":\"male\",\"email\":\"" + randomDataGenerator.createRandomEmail() + "\",\"status\":\"active\"}";

        // Perform a POST request to create the user
        Response response = RestAssured.given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", bearerToken)
                .body(requestBody)
                .post("/public/v2/users");
        logger.info("Create Status: " + response.getStatusCode());
        logger.info("Create User" + response.asString());

        //Assertion to verify successful creation status code
        Assert.assertEquals(response.getStatusCode(), 201);

        //extracting the generated userid
        userId = response.jsonPath().getInt("id");
        logger.info("User id " + userId);
    }

    @Test(priority = 2, description = "Verify create user with invalid data inputs")
    public void createInvalidUser() {
        RestAssured.baseURI = baseURI;
        String invalidRequestBody = "{\"name\":null,\"gender\":\"male\",\"email\":null,\"status\":\"active\"}";

        // Perform a POST request to create the user
        Response response = RestAssured.given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", bearerToken)
                .body(invalidRequestBody)
                .post("/public/v2/users");
        logger.info("Invalid Create Status : " + response.getStatusCode());
        logger.info("Create Invalid User" + response.asString());

        //Assertion to verify unsuccessful create status code
        Assert.assertEquals(response.getStatusCode(), 422);
        //Assertion to verify response body message
        Assert.assertEquals(response.jsonPath().getString("[0].message"), "can't be blank", "Unexpected error message for email field");
        Assert.assertEquals(response.jsonPath().getString("[1].message"), "can't be blank", "Unexpected error message for name field");
    }

    @Test(priority = 3, description = "Verify update user details with updated data for a valid user")
    public void updateValidUser() {
        RestAssured.baseURI = baseURI;
        String updateRequestBody = "{\"name\":\"mary\",\"gender\":\"female\",\"email\":\"mbl09@gmail.com\",\"status\":\"inactive\"}";

        // Perform a PUT request to update the user
        Response updateResponse = RestAssured.given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", bearerToken)
                .body(updateRequestBody)
                .put("/public/v2/users/" + userId);
        logger.info(" Update Status : " + updateResponse.getStatusCode());
        logger.info("Update User" + updateResponse.asString());

        //Assertion to verify successful update status code
        Assert.assertEquals(updateResponse.getStatusCode(), 200);
        //Assertion to verify updated user details in response body
        Assert.assertEquals(updateResponse.jsonPath().getString("name"), "mary", "User name updated successfully");
        Assert.assertEquals(updateResponse.jsonPath().getString("email"), "mbl09@gmail.com", "User email updated successfully");
        Assert.assertEquals(updateResponse.jsonPath().getString("status"), "inactive", "User status updated successfully");
        Assert.assertEquals(updateResponse.jsonPath().getString("gender"), "female", "User gender updated successfully");
    }

    @Test(priority = 4, description = "Verify update user details with invalid data for valid user")
    public void updateInvalidUser() {
        RestAssured.baseURI = baseURI;
        String requestBody = "{\"name\":\"mary\",\"gender\":\"female\",\"email\":\"mary0178.gmail.com\",\"status\":\"inactive\"}";

        // Perform a PUT request to update the user with no authorization token
        Response updateResponse = RestAssured.given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", bearerToken)
                .body(requestBody)
                .put("/public/v2/users/" + userId);
        logger.info("Invalid Update Status : " + updateResponse.getStatusCode());
        logger.info("Update Invalid User" + updateResponse.asString());

        //Assertion to verify successful update status code
        Assert.assertEquals(updateResponse.getStatusCode(), 422);
        //Assertion to verify response body error message
        Assert.assertEquals(updateResponse.getStatusCode(), 422, "is invalid");
    }

    @Test(priority = 5, description = "Verify fetch user details of a valid user")
    public void fetchValidUser() {
        RestAssured.baseURI = baseURI;

        //Perform GET request to fetch user details
        Response fetchResponse = RestAssured.given()
                .header("Accept", "application/json")
                .header("Authorization", bearerToken)
                .get("/public/v2/users/" + userId);
        logger.info("Fetch Status: " + fetchResponse.getStatusCode());
        logger.info("Fetch User" + fetchResponse.asString());

        //Assertion to verify successful fetch status code
        Assert.assertEquals(fetchResponse.getStatusCode(), 200);
        // Assert to verify correct userId is fetched
        Assert.assertEquals(fetchResponse.jsonPath().getInt("id"), userId, "Correct user ID fetched");
    }

    @Test(priority = 6, description = "Verify fetch user details of an invalid user")
    public void fetchInvalidUser() {
        RestAssured.baseURI = baseURI;

        // Perform a POST request to fetch the invalid user
        Response fetchResponse = RestAssured.given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", bearerToken)
                .get("/public/v2/users/" + 748839);
        logger.info("Invalid Fetch Status : " + fetchResponse.getStatusCode());
        logger.info("Invalid Fetch Response Body: " + fetchResponse.asString());

        //assertion to verify unsuccessful fetch status code
        Assert.assertEquals(fetchResponse.getStatusCode(), 404);
        //assertion to verify response body error message
        Assert.assertEquals(fetchResponse.getStatusCode(), 404, "Resource not found");
    }

    @Test(priority = 7, description = "Verify delete user operation for a valid user")
    public void deleteValidUser() {
        RestAssured.baseURI = baseURI;

        // Perform DELETE request to delete the user
        Response deleteResponse = RestAssured.given()
                .header("Accept", "application/json")
                .header("Authorization", bearerToken)
                .delete("/public/v2/users/" + userId);
        logger.info("Delete Status : " + deleteResponse.getStatusCode());
        logger.info("Delete User" + deleteResponse.asString());

        // Assertion to verify successful deletion status code
        Assert.assertEquals(deleteResponse.getStatusCode(), 204);
        // Assertion to verify empty response body
        Assert.assertTrue(deleteResponse.getBody().asString().isEmpty(), "Response body is empty after deletion");
    }

    @Test(priority = 8, description = "Verify delete user with invalid authorization token")
    public void deleteInvalidUser() {
        RestAssured.baseURI = baseURI;

        // Perform a POST request to delete user
        Response deleteResponse = RestAssured.given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 5dc321a465cb04decc6e6ac9c2eaf6a0064d960cde7ab93c6caa055")
                .delete("/users/8788");

        logger.info("Invalid Delete Status : " + deleteResponse.getStatusCode());
        logger.info("Invalid Delete Response Body: " + deleteResponse.asString());

        //Assertion to verify status code
        Assert.assertEquals(deleteResponse.getStatusCode(), 404);
    }
}























