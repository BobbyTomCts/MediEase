package org.example.stepdefinitions;


import io.cucumber.datatable.DataTable;
import org.example.core.utils.Config;
import org.example.core.utils.DriverManager;
import org.example.pages.RegisterPage;
import io.cucumber.java.en.*;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class RegisterStepDefinitions {

    private RegisterPage registerPage;
    public RegisterStepDefinitions() {
        this.registerPage = new RegisterPage();
    }

    @Given("I am on the Registration page")
    public void iAmOnTheRegistrationPage() {
        DriverManager.get().get(Config.baseUrl() +"/register");
    }

    @When("I register the following new users:")
    public void iRegisterTheFollowingNewUsers(DataTable usersTable) {
        List<Map<String, String>> users = usersTable.asMaps(String.class, String.class);

        for (Map<String, String> user : users) {
            String name = user.get("name");
            String phone = user.get("phone");
            String email = user.get("email");
            String password = user.get("password");

            System.out.println("Attempting registration for: " + email);

            iAmOnTheRegistrationPage();

            registerPage.registerUser(name, phone, email, password);
            try { Thread.sleep(5000); } catch (InterruptedException e) {}

        }
    }

    @Then("I should be redirected to {string} page")
    public void iShouldBeRedirectedToLoginPage(String expectedUrl) {
        String currentUrl = registerPage.getCurrentUrl();
        Assert.assertEquals(currentUrl, expectedUrl, "Redirection failed.");
        DriverManager.get().quit();
    }

    @When("I attempt to register with an existing email {string} and password {string}")
    public void iAttemptToRegisterWithExistingEmail(String email, String password) {
        String name = "Hari Priya";
        String phone = "9998887770";
        registerPage.registerUser(name, phone, email, password);
        try { Thread.sleep(3000); } catch (InterruptedException e) {}
    }
    @Then("I should see the registration error message {string}")
    public void iShouldSeeTheRegistrationErrorMessage(String expectedError) {
        String actualError = registerPage.getRegistrationErrorMessage();
        Assert.assertEquals(expectedError, actualError,
                "Registration error message did not match expected text.");
    }

    @Then("I should remain on the Registration page")
    public void iShouldRemainOnTheRegistrationPage() {
        String currentUrl = DriverManager.get().getCurrentUrl();
        Assert.assertTrue(currentUrl.endsWith("/register"),
                "Expected to remain on the Registration page, but navigated to: " + currentUrl);
    }

}