package org.example.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.core.utils.DriverManager;
import org.example.pages.DependentsManagementPage;
import org.testng.Assert;

public class DependentsManagementStepDefinitions {

    private DependentsManagementPage dependentsPage;

    public DependentsManagementStepDefinitions() {
        this.dependentsPage = new DependentsManagementPage();
    }

    @Then("{string} button should be enabled")
    public void theSetupButtonShouldBeEnabled(String buttonName) {
        if (buttonName.contains("Skip & Complete Setup")) {
            Assert.assertTrue(dependentsPage.isSkipButtonEnabled(), buttonName + " should be enabled.");
        } else if (buttonName.contains("Complete Setup")) {
            Assert.assertTrue(dependentsPage.isCompleteSetupButtonEnabled(), buttonName + " should be enabled.");
        } else {
            Assert.fail("Unknown button name: " + buttonName);
        }
    }

    @When("I click on {string} button")
    public void iClickTheSetupButton(String buttonName) {
        if (buttonName.contains("Skip & Complete Setup")) {
            dependentsPage.clickSkipAndCompleteSetup();
        } else if (buttonName.contains("Complete Setup")) {
            dependentsPage.clickCompleteSetup();
        } else if (buttonName.contains("Add Dependant")) {
            dependentsPage.clickAddDependant();
        } else {
            Assert.fail("Unknown button name: " + buttonName);
        }
    }

    @Then("the {string} form should be visible")
    public void theAddDependantModalShouldBeVisible(String modalName) {
        Assert.assertTrue(dependentsPage.isDependantFormVisible(),
                modalName + " modal is not visible.");
    }

    @Then("{string} form should be visible")
    public void theEditDependantFormShouldBeVisible(String formName) {
        Assert.assertTrue(dependentsPage.isEditDependantFormVisible(),
                formName + " modal is not visible.");
    }
    @When("I fill the dependant form with name {string} age {int} gender {string} and relation {string} and click submit")
    public void iFillTheDependantForm(String name, int age, String gender, String relation) throws InterruptedException {
        dependentsPage.fillAndSubmitDependentForm(name, age, gender, relation);
        Thread.sleep(1000);
    }

    @Then("I should be navigated to the User Dashboard")
    public void iShouldBeNavigatedToTheUserDashboard() {
        Assert.assertTrue(dependentsPage.isInsuranceSetupComplete(),
                "Final setup failed. Did not navigate to the User Dashboard (http://localhost:4200/user-dashboard). Current URL: " + DriverManager.get().getCurrentUrl());
    }

    @Then("the dependent with name {string} should be displayed on the page")
    public void theDependentShouldBeDisplayed(String dependantName) {
        Assert.assertTrue(dependentsPage.isDependantDisplayed(dependantName),
                "Dependent '" + dependantName + "' was not found on the page.");
    }

    @Given("the dependent with name {string} is displayed")
    public void theDependentIsDisplayed(String dependantName) {
        Assert.assertTrue(dependentsPage.isDependantDisplayed(dependantName),
                "Pre-requisite failed: Dependent '" + dependantName + "' is not displayed for editing/removal.");
    }

    @When("I click the {string} button for dependent {string}")
    public void iClickTheButtonForDependent(String buttonText, String dependantName) {
        if (buttonText.equalsIgnoreCase("Edit")) {
            dependentsPage.clickEditButtonForDependent(dependantName);
        } else if (buttonText.equalsIgnoreCase("Remove")) {
            dependentsPage.clickRemoveButtonForDependent(dependantName);
        } else {
            Assert.fail("Unsupported button text for dependent action: " + buttonText);
        }
    }

    @When("I modify the age to {int} and relation to {string} and click submit")
    public void iModifyTheDependentForm(int age, String relation) {
        dependentsPage.modifyAndSubmitDependentForm(age, relation);
    }

    @Then("the dependent {string} should display age {int} and relation {string}")
    public void theDependentShouldDisplayUpdatedDetails(String dependantName, int age, String relation) {
        Assert.assertTrue(dependentsPage.isDependantDetailsCorrect(dependantName, age, relation),
                "Dependent '" + dependantName + "' did not display the correct updated details (Age: " + age + ", Relation: " + relation + ").");
    }

    @When("I confirm the removal")
    public void iConfirmTheRemoval() throws InterruptedException {
        dependentsPage.confirmRemoval();
    }

    @Then("the dependent with name {string} and relation {string} should not be displayed on the page")
    public void theDependentShouldNotBeDisplayed(String dependantName,String relation) {
        Assert.assertTrue(dependentsPage.isDependantRemoved(dependantName,relation),
                "Dependent '" + dependantName + "' was still found on the page after removal.");
    }
}
