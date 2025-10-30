package org.example.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.core.utils.Config;
import org.example.core.utils.DriverManager;
import org.example.pages.InsuranceSelectionPage;
import org.example.pages.LoginPage;
import org.testng.Assert;


public class InsuranceSelectionStepDefinitions {

    private InsuranceSelectionPage insurancePage;
    private LoginPage loginPage;
    private String selectedPackage;
    public InsuranceSelectionStepDefinitions() {
        this.insurancePage = new InsuranceSelectionPage();
        this.loginPage = new LoginPage();
    }

    @Given("I navigate to the login page")
    public void iNavigateToTheLoginPage() throws InterruptedException {
        DriverManager.get().get(Config.baseUrl() + "/login");
        Thread.sleep(2000);
    }

    @When("I log in with email {string} and password {string}")
    public void iLogInWithEmailAndPassword(String email, String password) throws InterruptedException {
        loginPage.login(email,password);
        Thread.sleep(2000);
    }

    @Then("I should be on the Insurance Selection page")
    public void iShouldBeOnTheInsuranceSelectionPage() throws InterruptedException {
        String expectedUrl = Config.baseUrl() + "/insurance-selection";
        Thread.sleep(2000);
        Assert.assertEquals(DriverManager.get().getCurrentUrl(),expectedUrl,
                "Navigation failed. Current URL is: " + DriverManager.get().getCurrentUrl());
    }

    @Then("the welcome message should start with {string}")
    public void theWelcomeMessageShouldStartWith(String expectedPrefix) {
        Assert.assertTrue(
                insurancePage.getWelcomeMessage().startsWith(expectedPrefix),
                "FAIL: Welcome message is not displayed properly or does not start with \"" + expectedPrefix + "\"");
    }

    @Then("the {string} button should be disabled")
    public void theProceedToDependentsButtonShouldBeDisabled(String buttonName) {
        Assert.assertFalse(insurancePage.isProceedButtonEnabled(),
                buttonName + " button should be disabled before selection.");
    }


    @When("I select the {string} insurance package")
    public void iSelectTheInsurancePackage(String packageName) throws InterruptedException {
        this.selectedPackage = packageName;
            insurancePage.selectPackage(packageName);
        Assert.assertTrue(insurancePage.isPackageSelected(packageName),
                "Package " + packageName + " was clicked but failed to be marked as 'selected'.");
            //Thread.sleep(2000);

    }
    @Then("the {string} insurance package should be marked as selected")
    public void theInsurancePackageShouldBeMarkedAsSelected(String packageName) {
        Assert.assertTrue(insurancePage.isPackageSelected(packageName),
                "Package " + packageName + " was not marked as 'Selected'.");
    }
    @Then("the {string} button should be enabled")
    public void theProceedToDependentsButtonShouldBeEnabled(String buttonName) throws InterruptedException {
        if (selectedPackage != null) {
            Assert.assertTrue(insurancePage.isPackageSelected(selectedPackage), "Precondition failed: Package was not selected.");
        }
        Assert.assertTrue(insurancePage.isProceedButtonEnabled(),
                buttonName + " button should be enabled after package selection.");
    }


    @When("I click the {string} button")
    public void iClickTheProceedToDependentsButton(String buttonName) throws InterruptedException {
        insurancePage.clickProceedToDependants();
    }

    @Then("I should be navigated to the Dependents Management page")
    public void iShouldBeNavigatedToTheDependentsManagementPage() {
        String currentUrl = DriverManager.get().getCurrentUrl();
        String expectedUrl = "http://localhost:4200/dependants-management?setup=true";
        Assert.assertEquals(currentUrl, expectedUrl, "Proceeding to the dependents form failed.");
    }

}

