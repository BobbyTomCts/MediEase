package org.example.restapi.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.example.restapi.models.Dependant;
import org.example.restapi.models.Insurance;
import org.example.restapi.services.InsuranceApiService;
import org.example.restapi.utils.TokenManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class InsuranceStepDefinitions {
    private InsuranceApiService insuranceApiService = new InsuranceApiService();
    private TokenManager tokenManager = TokenManager.getInstance();
    private Response response;
    private static Long CREATED_INSURANCE_ID; // To store IDs for subsequent tests
    private String currentToken;

    @Given("Employee with ID {long} has existing insurance")
    public void employeeWithIdHasExistingInsurance(Long empId) {
        // Ensure token is available
        currentToken = tokenManager.getUserToken();
    }

    @Given("A dependant with ID {long} exists for empId {long}")
    public void aDependantWithIDExistsForEmpId(Long dependantId, Long empId) {
        currentToken = tokenManager.getUserToken();
    }

    @Given("A dependant with ID {long} exists and needs to be deleted")
    public void aDependantWithIDExistsAndNeedsToBeDeleted(Long dependantId) {
        currentToken = tokenManager.getUserToken();
    }

    @Given("Employee with ID {long} has dependants")
    public void employeeWithIdHasMultipleDependants(Long empId) {
        currentToken = tokenManager.getUserToken();
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        assertEquals("Incorrect status code returned.", expectedStatusCode, response.getStatusCode());
    }

    // --- Insurance Packages Steps ---
    @When("a GET request is sent to the {string}")
    public void aGETRequestIsSentTo(String endpoint) {
        currentToken = tokenManager.getUserToken();
        if (endpoint.equals("/packages")) {
            response = insuranceApiService.getAllPackages(currentToken);
        } else {
            response = insuranceApiService.getDependants(Long.valueOf(endpoint.replace("/dependants/", "")), currentToken);
        }
    }

    @And("the response body should be a list of InsuranceType packages")
    public void theResponseBodyShouldBeAListOfInsuranceTypePackages() {
        response.then().extract().as(org.example.restapi.models.InsuranceType[].class); // Deserialization test
        System.out.println(response.then().extract().asString());
        assertTrue("Response list should not be empty", response.jsonPath().getList("$").size() > 0);
    }

    // --- Create Insurance Steps ---
    @When("a POST request is sent to {string} with empId {long} and packageId {long}")
    public void aPOSTRequestIsSentToWithEmpIdAndPackageId(String endpoint, Long empId, Long packageId) {
        currentToken = tokenManager.getUserToken();
        response = insuranceApiService.createInsurance(empId, packageId, currentToken);
    }

    @And("the response contains an Insurance object for empId {long}")
    public void theResponseContainsAnInsuranceObjectForEmpId(Long empId) {
        if (response.getStatusCode() != 200) {
            throw new AssertionError("Expected status 200 but got " + response.getStatusCode() + ". Response body was: " + response.asString());
        }
        Insurance insurance = response.as(Insurance.class);
        CREATED_INSURANCE_ID = insurance.getInsuranceId();
        assertEquals("Returned insurance empId mismatch", empId, insurance.getEmpId());
    }

    @And("the Insurance object {string} is not null")
    public void theInsuranceObjectIsNotNull(String field) {
        Insurance insurance = response.as(Insurance.class);
        if (field.equalsIgnoreCase("packageName")) {
            assertNotNull("Package Name should not be null", insurance.getPackageName());
        }
    }

    // --- Get Insurance By EmpId Steps ---
    @When("a GET request is sent to {long}")
    public void aGETRequestIsSentToEmpId(Long empId) {
        currentToken = tokenManager.getUserToken();
        response = insuranceApiService.getDependants(empId, currentToken);
//        response = insuranceApiService.getDependants(Long.valueOf(endpoint.replace("/dependants/", "")));
        System.out.println(response.then().extract().asString());
    }

    @And("the response Insurance object {string} is {long}")
    public void theResponseInsuranceObjectIs(String field, Long expectedValue) {
        Insurance insurance = response.as(Insurance.class);
        if (field.equalsIgnoreCase("empId")) {
            assertEquals("Insurance empId mismatch", expectedValue, insurance.getEmpId());
        }
    }

    // --- Dependant Steps ---
    @When("a POST request is sent to {string} for empId {long} with name {string}, age {int}, gender {string}, and relation {string}")
    public void aPOSTRequestIsSentToForEmpIdWithNameAgeGenderAndRelation(String endpoint, Long empId, String name, Integer age, String gender, String relation) {
        currentToken = tokenManager.getUserToken();
        response = insuranceApiService.addDependant(empId, name, age, gender, relation, currentToken);
    }

    @And("the response Dependant object {string} is {string}")
    public void theResponseDependantObjectIs(String field, String expectedValue) {
        Dependant dependant = response.as(Dependant.class);
        if (field.equalsIgnoreCase("name")) {
            assertEquals("Dependant name mismatch", expectedValue, dependant.getName());
        }
    }

    @And("the response body should be a list of Dependant objects")
    public void theResponseBodyShouldBeAListOfDependantObjects() {
        response.then().extract().as(org.example.restapi.models.Dependant[].class);
        System.out.println(response.then().extract());// Deserialization test
    }

    @And("the list size should be greater than {int}")
    public void theListSizeShouldBeGreaterThan(int expectedMinSize) {
        assertTrue("Dependants list size is not greater than " + expectedMinSize, response.jsonPath().getList("$").size() > expectedMinSize);
    }

    @When("a PUT request is sent to {string} with name {string}, age {int}, gender {string}, and relation {string}")
    public void aPUTRequestIsSentToWithDetails(String endpoint, String name, Integer age, String gender, String relation) {
        currentToken = tokenManager.getUserToken();
        Long dependantId = Long.valueOf(endpoint.replace("/dependant/update/", ""));
        response = insuranceApiService.updateDependant(dependantId, name, age, gender, relation, currentToken);
        System.out.println(response.then().extract().asString());
    }

    @And("the response Dependant object {string} is {int}")
    public void theResponseDependantObjectIs(String field, int expectedValue) {
        Dependant dependant = response.as(Dependant.class);
        if (field.equalsIgnoreCase("age")) {
            assertEquals("Dependant age mismatch", Integer.valueOf(expectedValue), dependant.getAge());
        }
    }

    @When("a DELETE request is sent to {string}")
    public void aDELETERequestIsSentTo(String endpoint) {
        currentToken = tokenManager.getUserToken();
        Long dependantId = Long.valueOf(endpoint.replace("/dependant/delete/", ""));
        response = insuranceApiService.deleteDependant(dependantId, currentToken);
    }

    @And("the response message is {string}")
    public void theResponseMessageIs(String expectedMessage) {
        assertEquals("Response message mismatch", expectedMessage, response.asString());
    }
}