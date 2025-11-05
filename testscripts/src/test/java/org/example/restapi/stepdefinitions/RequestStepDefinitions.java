package org.example.restapi.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.example.restapi.models.Request;
import org.example.restapi.models.User;
import org.example.restapi.services.RequestService;
import org.example.restapi.services.UserService;
import org.example.restapi.utils.TokenManager;
import org.testng.Assert;

import java.util.List;
import java.util.Map;


public class RequestStepDefinitions {

    private final RequestService requestService;
    private Response response;
    private Long createdRequestId;
    private final UserService userService;
    private final TokenManager tokenManager;
    private static final Long TEST_EMPLOYEE_ID = 1L;
    private static final Long TEST_HOSPITAL_ID = 42L;
    private String adminToken;
    private String userToken;
    Map<String,String> map;
    
    public RequestStepDefinitions() {
        this.requestService = new RequestService();
        this.userService = new UserService();
        this.tokenManager = TokenManager.getInstance();
    }


    @Given("the admin is logged in with email {string} and password {string}")
    public void theAdminIsLoggedIn(String email, String password) {
        User adminUser = new User(email, password);
        Response loginResponse = userService.postRequest1("/api/users/login", adminUser);
        adminToken = loginResponse.jsonPath().getString("token");
        Assert.assertNotNull(adminToken, "Admin token retrieval failed. Check credentials/setup.");
    }
    @Given("an employee creates a new request with details:")
    public void anEmployeeCreatesANewRequestWithDetails(DataTable data) {
        userToken = tokenManager.getUserToken();
        map = data.asMaps(String.class,String.class).get(0);
        Long empId = Long.parseLong(map.get("empId"));
        Double amount = Double.parseDouble(map.get("amount"));
        Long hospitalId = Long.parseLong(map.get("hospitalId"));
        response = requestService.createRequest(empId, amount, hospitalId, userToken);
        System.out.println(response.then().log().all());

        if (response.getStatusCode() == 200) {
            createdRequestId = response.jsonPath().getLong("requestId");
            Assert.assertTrue(createdRequestId != null && createdRequestId > 0, "Request ID was not generated.");
        }
    }

    @Then("the request is created with PENDING status")
    public void theRequestIsCreatedWithPendingStatus() {
        System.out.println("Status code:"+response.getStatusCode());
        System.out.println("Status: "+response.jsonPath().getString("status"));
        Assert.assertEquals(response.getStatusCode(), 200, "Request creation failed.");
        Assert.assertEquals(response.jsonPath().getString("status"), "PENDING", "Status is not PENDING.");
    }

    @When("the admin approves the created request")
    public void theAdminApprovesTheCreatedRequest() {
        Assert.assertNotNull(createdRequestId, "No request ID found to approve.");
        Assert.assertNotNull(adminToken, "Admin must be logged in (token missing) to approve request.");
        response = requestService.approveRequest(createdRequestId, adminToken);
    }
    @When("the admin rejects the created request")
    public void theAdminRejectsTheCreatedRequest() {
        Assert.assertNotNull(createdRequestId, "No request ID found to reject.");
        Assert.assertNotNull(adminToken, "Admin must be logged in (token missing) to reject request.");
        response = requestService.rejectRequest(createdRequestId, adminToken);
    }

    @Then("the request status is updated to {string}")
    public void theRequestStatusIsUpdatedTo(String expectedStatus) {
        Assert.assertEquals(response.getStatusCode(), 200, "Update request failed.");
        Assert.assertEquals(response.jsonPath().getString("status"), expectedStatus, "Status mismatch.");

        if ("APPROVED".equals(expectedStatus)) {
            Assert.assertNotNull(response.jsonPath().getDouble("approvedAmount"), "Approved amount missing.");
        }
    }

    // Retrieval/Filtering Steps
    @When("the user gets all requests")
    public void theUserGetsAllRequests() {
        userToken = tokenManager.getUserToken();
        response = requestService.getAllRequests(userToken);
        System.out.println(response.then().log().all());
    }

    @When("the user gets requests for employee ID {long}")
    public void theUserGetsRequestsForEmployeeID(long empId) {
        userToken = tokenManager.getUserToken();
        response = requestService.getRequestsByEmpId(empId, userToken);
        System.out.println(response.then().log().all());
    }

    @When("the user gets requests filtered by status {string}")
    public void theUserGetsRequestsFilteredByStatus(String status) {
        userToken = tokenManager.getUserToken();
        response = requestService.getRequestsByStatus(status, userToken);
    }

    @When("the user gets requests filtered by status {string} and date range from {string} to {string}")
    public void theUserGetsRequestsFilteredByStatusAndDateRange(String status, String startDateStr, String endDateStr) {
        userToken = tokenManager.getUserToken();
        response = requestService.getFilteredRequests(status, startDateStr, endDateStr, userToken);
    }

    @Then("the response list is not empty")
    public void theResponseListIsNotEmpty() {
        Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve list.");
        List<Request> requests = response.jsonPath().getList("$");
        System.out.println(requests);
        Assert.assertFalse(requests.isEmpty(), "The request list is empty.");
    }

    @Then("all requests in the response have status {string}")
    public void allRequestsInTheResponseHaveStatus(String expectedStatus) {
        Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve list.");
        List<String> statuses = response.jsonPath().getList("status");
        System.out.println(statuses);
        Assert.assertFalse(statuses.isEmpty(), "List is empty, cannot verify status.");

        boolean allMatch = statuses.stream().allMatch(s -> s.equalsIgnoreCase(expectedStatus));
        Assert.assertTrue(allMatch, "Not all requests had the expected status: " + expectedStatus);
    }
    @Then("the request response status code is {int}")
    public void theRequestResponseStatusCodeIs(int expectedStatus) {
        Assert.assertNotNull(response, "Response object is null. The preceding API call failed ");
        Assert.assertEquals(response.getStatusCode(), expectedStatus, "Expected status code mismatch.");
    }

}

