package org.example.stepdefinitions;

import org.example.core.utils.Config;
import org.example.core.utils.DriverManager;
import org.example.pages.AdminPage;
import org.example.pages.LoginPage;
import io.cucumber.java.en.*;
import org.example.pages.UserDashboardPage;
import org.testng.Assert;

public class AdminStepDefinitions {

    private AdminPage adminPage;
    private LoginPage loginPage;
    private UserDashboardPage userDashboardPage;

    public AdminStepDefinitions()
    {
        this.loginPage = new LoginPage();
        this.userDashboardPage = new UserDashboardPage();
        this.adminPage = new AdminPage();
    }

    @Given("I am on the Admin page")
    public void iAmOnTheAdminPage() {
        DriverManager.get().get(Config.baseUrl() + "/login");
        loginPage.login("admin@gmail.com","admin123");
    }

    @When("I click the {string} button for Request id {string}")
    public void iClickTheButtonForRequestId(String action, String requestId)
    {
        if(action.equalsIgnoreCase("APPROVE"))
            adminPage.clickApproveButton(requestId);
        else if(action.equalsIgnoreCase("REJECT"))
            adminPage.clickRejectButton(requestId);
        else {
            throw new IllegalArgumentException("Unsupported action: " + action);
        }
        try { Thread.sleep(500); } catch (InterruptedException e) {}
    }

    @Then("The status for Request Id {string} should change to {string}")
    public void theStatusForRequestIdShouldChangeTo(String requestId,String expectedStatus)
    {
        String actualStatus = adminPage.getStatus(requestId);
        Assert.assertEquals(actualStatus,expectedStatus,"The status for Request ID " + requestId + " was expected to be " + expectedStatus +
                " but found " + actualStatus);
    }

    @When("I click on logout button")
    public void iClickOnLogoutButton()
    {
        adminPage.clickLogoutButton();
        try { Thread.sleep(5000); } catch (InterruptedException e) {}
    }

    @Then("I should be redirected to login page")
    public void iShouldBeRedirectedToLoginPage()
    {
        String expectedUrl = Config.baseUrl() + "/login";
        Assert.assertEquals(DriverManager.get().getCurrentUrl(),expectedUrl,"Logout failed");
    }

    @Given("I login as user with email {string} and password {string}")
    public void iLoginAsUserWithEmailAndPassword(String email, String password)
    {
        loginPage.login(email,password);
        try { Thread.sleep(5000); } catch (InterruptedException e) {}
    }

    @Then("the user sees status {string} for Request Id {string} on their dashboard")
    public void theUserSeesStatusForRequestIdOnTheirDashboard(String expectedStatus,String requestId)
    {
        String actualStatus = userDashboardPage.checkClaimStatus(requestId);
        Assert.assertEquals(actualStatus,expectedStatus,"Logout failed");
    }

    @When("I select the status filter {string}")
    public void iSelectTheStatusFilter(String status) {
        System.out.println("Step: Filtering by status: " + status);
        adminPage.selectStatusFilter(status);
        try { Thread.sleep(500); } catch (InterruptedException e) {}

    }

    @Then("the table should only show requests with status {string}")
    public void theTableShouldOnlyShowRequestsWithStatus(String expectedStatus) {
        boolean allMatch = adminPage.verifyAllStatusesAre(expectedStatus);
        Assert.assertTrue(allMatch, "The filter failed. Not all visible requests match the expected status: " + expectedStatus);
    }

    @When("I filter requests from date {string} to date {string}")
    public void iFilterRequestsFromDateToDate(String fromDate, String toDate) {
        System.out.println("Step: Filtering requests from " + fromDate + " to " + toDate);
        adminPage.enterFromDate(fromDate);
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        adminPage.enterToDate(toDate);
        try { Thread.sleep(500); } catch (InterruptedException e) {}

    }

    @Then("the table should only show requests within the date range {string} to {string}")
    public void theTableShouldOnlyShowRequestsWithinTheDateRange(String fromDate, String toDate) {
        boolean allDatesValid = adminPage.verifyDatesAreWithinRange(fromDate, toDate);
        Assert.assertTrue(allDatesValid, "The date filter failed. Not all dates in the table fall within the range: " + fromDate + " to " + toDate);
    }

    @When("I click the Clear Filters button")
    public void iClickTheClearFiltersButton() {
        System.out.println("Step: Clicking Clear Filters button.");
        adminPage.clickClearFilters();
        try { Thread.sleep(500); } catch (InterruptedException e) {}

    }

    @Then("the status filter should be reset to {string}")
    public void theStatusFilterShouldBeResetToAll(String expectedResetText) {
        boolean isReset = adminPage.verifyStatusFilterIsReset();
        Assert.assertTrue(isReset, "The status filter did not reset. Expected selected option to contain 'ALL' but it did not.");
    }

    @Then("the date filters should be cleared")
    public void theDateFiltersShouldBeCleared() {
        boolean areDatesCleared = adminPage.verifyDateInputsAreReset();
        Assert.assertTrue(areDatesCleared, "The date input fields did not clear after clicking 'Clear Filters'.");
    }

}