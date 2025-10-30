package org.example.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.core.utils.Config;
import org.example.core.utils.DriverManager;
import org.example.pages.LoginPage;

import org.testng.Assert;


public class LoginStepDefinitions {

    private LoginPage loginPage;
    public LoginStepDefinitions() {
        this.loginPage = new LoginPage();
    }

    @Given("I am on the Login page")
    public void iAmOnTheLoginPage() {
        DriverManager.get().get(Config.baseUrl() + "/login");
    }

    @When("I log in with valid credentials {string} and {string}")
    public void iLogInWithValidCredentials(String email, String password) {
        System.out.println("Step: Logging in with " + email);
        loginPage.login(email, password);
    }

    @Then("I should be redirected to the dashboard or insurance selection page")
    public void iShouldBeRedirectedToTheDashboardOrInsuranceSelectionPage() {
        try { Thread.sleep(3000); } catch (InterruptedException e) {}

        String currentUrl = loginPage.getCurrentUrl();
        System.out.println("Assertion: Current URL is " + currentUrl);

        boolean isCorrectPage = currentUrl.equals("http://localhost:4200/user-dashboard")
                || currentUrl.equals("http://localhost:4200/insurance-selection");

        Assert.assertTrue(isCorrectPage, "Login failed. Current URL is: " + currentUrl);
    }
    @Then("I should be redirected to the admin dashboard")
    public void iShouldBeRedirectedToTheAdminDashboard() {
        try { Thread.sleep(3000); } catch (InterruptedException e) {}

        String expectedUrl = Config.baseUrl() +"/admin-dashboard";
        String currentUrl = loginPage.getCurrentUrl();
        System.out.println("Assertion: Current URL is " + currentUrl);

        Assert.assertEquals(currentUrl, expectedUrl, "Admin login failed. Current URL is: " + currentUrl);
    }
    @When("I click on the {string} link")
    public void iClickOnTheLink(String linkName) {
        if ("Create Account".equals(linkName)) {
            System.out.println("Step: Clicking Create Account link.");
            loginPage.clickCreateAccount();
        } else {
            throw new IllegalArgumentException("Unknown link name: " + linkName);
        }
    }

    @Then("I should be redirected to the {string} page")
    public void iShouldBeRedirectedToThePage(String expectedUrl) {
        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        String currentUrl = loginPage.getCurrentUrl();
        System.out.println("Assertion: Current URL is " + currentUrl);

        Assert.assertEquals(currentUrl, expectedUrl, "Redirection failed. Expected: " + expectedUrl + " but found: " + currentUrl);
    }

    @Then("I should see the invalid password error message")
    public void iShouldSeeTheInvalidPasswordErrorMessage() {
        Assert.assertTrue(loginPage.isInvalidPasswordErrorDisplayed(), "Invalid password error message was not displayed.");
    }

    @Then("I should see the user not found error message")
    public void iShouldSeeTheUserNotFoundErrorMassage() {
        Assert.assertTrue(loginPage.isUserNotFoundErrorDisplayed(), "User not found error message was not displayed.");
    }

}

