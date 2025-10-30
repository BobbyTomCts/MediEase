package org.example.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.core.utils.DriverManager;
import org.example.pages.ProfilePage;
import org.testng.Assert;

public class ProfilePageStepDefinitions {

    private ProfilePage profilePage;

    public ProfilePageStepDefinitions() {
        this.profilePage = new ProfilePage();
    }

    @When("I click {string} button")
    public void iClickTheButton(String buttonName) throws InterruptedException {
        switch (buttonName.toLowerCase()) {
            case "edit profile":
                profilePage.clickEditProfileButton();
                Thread.sleep(1000);
                break;
            case "save changes":
                profilePage.clickSaveChangesButton();
                break;
            case "cancel":
                profilePage.clickCancelButton();
                break;
            case "manage dependants":
                profilePage.clickManageDependentsButton();
                System.out.println("clicked");
                Thread.sleep(3000);
                break;
            default:
                throw new IllegalArgumentException("Unsupported button: " + buttonName);
        }
    }

    @And("I set the {string} to {string}")
    public void iSetTheFieldTo(String fieldName, String value) {
        switch (fieldName.toLowerCase()) {
            case "full name":
                profilePage.enterFullName(value);
                break;
            case "phone number":
                profilePage.enterPhoneNumber(value);
                break;
            default:
                throw new IllegalArgumentException("Unsupported field for editing: " + fieldName);
        }
    }

    @Then("I should see the success message {string}")
    public void iShouldSeeTheSuccessMessage(String expectedMessage) {
        String actualMessage = profilePage.getSuccessMessageText();
        Assert.assertTrue(actualMessage.contains(expectedMessage), "The success message is incorrect or not visible.");
    }

    @And("I should see the {string} displayed as {string}")
    public void iShouldSeeTheFieldDisplayedAs(String fieldName, String expectedValue) throws InterruptedException {
        String actualValue;
        Thread.sleep(2000);
        switch (fieldName.toLowerCase()) {
            case "full name":
                actualValue = profilePage.getDisplayedFullName();
                break;
            case "phone number":
                actualValue = profilePage.getDisplayedPhoneNumber();
                break;
            default:
                throw new IllegalArgumentException("Unsupported field for assertion: " + fieldName);
        }
        Assert.assertEquals(actualValue, expectedValue, fieldName + " value is incorrect after operation.");
    }

    @Then("I should see the {string} button")
    public void iShouldSeeTheEditProfileButton(String buttonName) {
        if (buttonName.equalsIgnoreCase("Edit Profile")) {
            Assert.assertTrue(profilePage.isEditProfileButtonVisible(),
                    "The 'Edit Profile' button should be visible after canceling edit mode.");
        } else {
            throw new IllegalArgumentException("Only 'Edit Profile' button visibility check implemented here.");
        }
    }

    @Then("I should be navigated to the page {string}")
    public void iShouldBeNavigatedToThePage(String expectedUrl) throws InterruptedException {
        Thread.sleep(2000);
        //DriverManager.get().navigate().to("http://localhost:4200/dependants-management");
        String currentUrl = profilePage.getCurrentUrl();
        Assert.assertEquals(currentUrl, expectedUrl,
                "The navigation URL is incorrect after clicking. Expected: " + expectedUrl + ", but found: " + currentUrl);
    }
}