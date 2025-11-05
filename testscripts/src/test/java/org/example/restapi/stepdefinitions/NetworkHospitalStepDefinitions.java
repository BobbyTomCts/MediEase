package org.example.restapi.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.example.restapi.models.NetworkHospital;
import org.example.restapi.services.NetworkHospitalService;
import org.example.restapi.utils.TokenManager;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class NetworkHospitalStepDefinitions {

    private final NetworkHospitalService hospitalService;
    private final TokenManager tokenManager;
    private Response response;
    private String currentToken;

    private static final String TEST_CITY = "Chennai";
    private static final String TEST_STATE = "Tamil Nadu";
    private static final Long TEST_ID = 1L;

    public NetworkHospitalStepDefinitions() {
        this.hospitalService = new NetworkHospitalService();
        this.tokenManager = TokenManager.getInstance();
    }

    @When("the user retrieves all network hospitals")
    public void theUserRetrievesAllNetworkHospitals() {
        currentToken = tokenManager.getUserToken();
        response = hospitalService.getAllHospitals(currentToken);
    }

    @When("the user searches for hospitals by city {string}")
    public void theUserSearchesForHospitalsByCity(String city) {
        currentToken = tokenManager.getUserToken();
        response = hospitalService.getHospitalsByCity(city, currentToken);
    }

    @When("the user searches for hospitals by state {string}")
    public void theUserSearchesForHospitalsByState(String state) {
        currentToken = tokenManager.getUserToken();
        response = hospitalService.getHospitalsByState(state, currentToken);
    }

    @When("the user retrieves the hospital with ID {long}")
    public void theUserRetrievesTheHospitalWithID(long id) {
        currentToken = tokenManager.getUserToken();
        response = hospitalService.getHospitalById(id, currentToken);
    }

    @Then("the hospital response status code is {int}")
    public void theHospitalResponseStatusCodeIs(int expectedStatus) {
        Assert.assertNotNull(response, "Response object is null. The preceding API call failed.");
        Assert.assertEquals(response.getStatusCode(), expectedStatus, "Expected status code mismatch.");
    }

    @Then("the hospital response list is not empty")
    public void theHospitalResponseListIsNotEmpty() {
        Assert.assertEquals(response.getStatusCode(), 200, "API call failed to return list.");
        List<NetworkHospital> hospitals = response.jsonPath().getList("$", NetworkHospital.class);
        Assert.assertFalse(hospitals.isEmpty(), "The hospital list should not be empty.");
    }

    @Then("the hospital response list is empty")
    public void theHospitalResponseListIsEmpty() {
        Assert.assertEquals(response.getStatusCode(), 200, "API call failed, cannot check list content.");
        List<NetworkHospital> hospitals = response.jsonPath().getList("$", NetworkHospital.class);
        Assert.assertTrue(hospitals.isEmpty(), "The hospital list should be empty for a no-result search.");
    }

    @Then("all hospitals in the response are in the city {string}")
    public void allHospitalsInTheResponseAreInTheCity(String expectedCity) {
        Assert.assertEquals(response.getStatusCode(), 200, "API call failed.");
        List<String> cities = response.jsonPath().getList("city");

        Assert.assertFalse(cities.isEmpty(), "List is empty, cannot verify city.");
        boolean allMatch = cities.stream().allMatch(c -> c.equalsIgnoreCase(expectedCity));
        Assert.assertTrue(allMatch, "Not all hospitals were in the expected city: " + expectedCity + ". Found cities: " + cities.stream().collect(Collectors.joining(", ")));
    }

    @Then("all hospitals in the response are in the state {string}")
    public void allHospitalsInTheResponseAreInTheState(String expectedState) {
        Assert.assertEquals(response.getStatusCode(), 200, "API call failed.");
        List<String> states = response.jsonPath().getList("state");

        Assert.assertFalse(states.isEmpty(), "List is empty, cannot verify state.");
        boolean allMatch = states.stream().allMatch(s -> s.equalsIgnoreCase(expectedState));
        Assert.assertTrue(allMatch, "Not all hospitals were in the expected state: " + expectedState + ". Found states: " + states.stream().collect(Collectors.joining(", ")));
    }

    @Then("the hospital name in the response is {string}")
    public void theHospitalNameInTheResponseIs(String expectedName) {
        Assert.assertEquals(response.getStatusCode(), 200, "API call failed.");
        String actualName = response.jsonPath().getString("hospitalName");
        Assert.assertEquals(actualName, expectedName, "Hospital name mismatch.");
    }

    @Then("the hospital has a copay percentage of {double}")
    public void theHospitalHasACopayPercentageOf(double expectedCopay) {
        Assert.assertEquals(response.getStatusCode(), 200, "API call failed.");
        Double actualCopay = response.jsonPath().getDouble("copayPercentage");
        Assert.assertEquals(actualCopay, expectedCopay, "Copay percentage mismatch.");
    }
}

