                                                REST ASSURED PROJECT ON API TESTING

INTRODUCTION: This project is a comprehensive API testing framework using TestNG and REST Assured. It covers CRUD operations on a user management API provided by the [GoRest] platform.
The framework includes a set of well-structured test cases, configuration details, and instructions for executing the tests.

OBJECTIVE: The primary goal of this project is to demonstrate effective API testing practices using popular Java libraries such as TestNG for test management and REST Assured for making API requests. The test cases cover a range of scenarios, including creating, updating, fetching, and deleting users, with an emphasis on handling both valid and invalid input data.

PRE-REQUISITES: Before running the tests, ensure that the following prerequisites are met:
- Java 8 or higher installed
- Maven installed
- Dependencies for TestNG and REST Assured configured in the project's `pom.xml` file

CONFIGURATION:

The project is configured to interact with the GoRest API. The configuration details, including the base URI and bearer token, are specified in the testCases class.

* baseURI = "https://gorest.co.in"

* bearerToken = "Bearer 5dc32152a465cc9b04dec8c4cca6e6ac9c2eaf6a0064d960cde7ab93c6caa055"

PACKAGE NAME: crudOperations

CLASSES:
1) randomDataGenerator: this class is used to generate random name and email for running the test case each time with different values , without giving it manually.


2) testCases: this class consist of all the methods required to perform CRUD Operations . Comprises of both positive and negative test cases.

METHODS:
1) createValidUser: Verify successful user creation with valid details.


* HTTP Method: POST

* Endpoint: /public/v2/users

* Assertions:

  => Verify status code 201 for successful create user.

* Also a user id is created which is furthur used in other CRUD Operations to deal with the same user.


2) createInvalidUser: Verify user creation with invalid data inputs.


* HTTP Method: POST

* Endpoint: /public/v2/users

* Assertions:

  => Verify status code 422 for invalid input.

  => Verify specific error messages for name and email fields.


3) updateValidUser: Verify updating user details with valid data for an existing user.


* HTTP Method: PUT

* Endpoint: /public/v2/users/{userId}

* Assertions:

  => Verify status code 200 for a successful update.

  => Verify updated user details in the response body.


4) updateInvalidUser: Verify updating user details with invalid data for an existing user.


* HTTP Method: PUT

* Endpoint: /public/v2/users/{userId}

* Assertions:

  => Verify status code 422 for invalid input.

  => Verify a specific error message for the email field.


5) fetchValidUser: Verify fetching user details of a valid user.


* HTTP Method: GET

* Endpoint: /public/v2/users/{userId}

* Assertions:

  => Verify status code 200 for a successful fetch.

  => Verify the correct user ID is fetched.


6) fetchInvalidUser: Verify fetching user details for a invalid user.


* HTTP Method: GET

* Endpoint: /public/v2/users/{invalidUserId}

* Assertions:

  =>  Verify status code 404 for a non-existent resource.


7) deleteValidUser: Verify deleting an existing user.


* HTTP Method: DELETE

* Endpoint: /public/v2/users/{userId}

* Assertions:

  => Verify status code 204 for a successful deletion.

  => Verify an empty response body.


8) deleteInvalidUser: Verify deleting a user with invalid authorization token.


* HTTP Method: DELETE

* Endpoint: /public/v2/users/{invalidUserId}

* Assertions:

  => Verify status code 404 for a non-existent resource.

OUTPUT:

After running the tests, review the console output for each test case. Assertions are used to validate the expected behavior of the API.