package org.example.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.core.utils.Config;
import org.example.core.utils.DriverManager;
import org.example.pages.UserDashboardPage;
import org.testng.Assert;

public class ClaimStepDefinitions {

    private UserDashboardPage dashboardPage;

    public ClaimStepDefinitions() {
        this.dashboardPage = new UserDashboardPage();
    }

    @Then("I should be on the User Dashboard page")
    public void iShouldBeOnTheUserDashboardPage() {
        DriverManager.get().get(Config.baseUrl() + "/user-dashboard");
        Assert.assertTrue(dashboardPage.isOnDashboardPage(), "Current URL is not the User Dashboard.");
    }

    @Then("I should see the welcome text starting with {string}")
    public void iShouldSeeTheWelcomeText(String expectedStart) {
        String actualText = dashboardPage.getWelcomeText();
        Assert.assertTrue(actualText.startsWith(expectedStart),
                "Welcome text did not start with '" + expectedStart + "'. Actual text: " + actualText);
    }

    @When("I click on the 'Submit New Claim' button")
    public void iClickTheSubmitNewClaimButton() throws InterruptedException {
        dashboardPage.clickSubmitNewClaim();
        try { Thread.sleep(2000); } catch (InterruptedException e) {}

    }

    @When("I fill the claim form with hospital {string} and amount {string}")
    public void iFillTheClaimForm(String hospitalName, String amount) {
        dashboardPage.fillClaimForm(hospitalName, amount);
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    @When("I click the 'Submit Claim' button in the popup")
    public void iClickTheSubmitClaimButtonInThePopup() {

        dashboardPage.submitClaim();
        try { Thread.sleep(2000); } catch (InterruptedException e) {}

    }

    @Then("the newest claim with requested amount {string} should have status {string}")
    public void theNewestClaimDetailsShouldMatch(String expectedAmount, String expectedStatus) {
        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        String[] actualDetails = dashboardPage.getNewestClaimDetails();
        String actualAmount = actualDetails[0];
        String actualStatus = actualDetails[1];

        Assert.assertEquals(expectedAmount, actualAmount,
                "Newest claim requested amount did not match. Expected: " + expectedAmount + ", Actual: " + actualAmount);

        Assert.assertEquals(expectedStatus.toUpperCase(), actualStatus,
                "Newest claim status did not match. Expected: " + expectedStatus.toUpperCase() + ", Actual: " + actualStatus);
    }
}
