package org.example.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.example.core.utils.DriverManager;
import org.example.pages.ProfilePage;
import org.example.restapi.services.UserService;
import org.example.restapi.utils.TokenManager;
import org.testng.Assert;

public class ProfilePageStepDefinitions {

    private ProfilePage profilePage;
    private UserService userService;
    private TokenManager tokenManager;
    private Long currentUserId;

    public ProfilePageStepDefinitions() {
        this.profilePage = new ProfilePage();
        this.userService = new UserService();
        this.tokenManager = TokenManager.getInstance();
        
        // Get user ID from localStorage via JavaScript - will be set after login
    }
    
    private void ensureUserIdLoaded() {
        if (currentUserId == null) {
            String userIdStr = (String) DriverManager.get().executeScript("return localStorage.getItem('userId');");
            if (userIdStr != null && !userIdStr.isEmpty()) {
                this.currentUserId = Long.parseLong(userIdStr);
                System.out.println("Loaded user ID from localStorage: " + currentUserId);
            } else {
                throw new RuntimeException("User ID not found in localStorage. Ensure user is logged in.");
            }
        }
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
                // Wait longer for backend API call to complete
                Thread.sleep(3000);
                
                // Check browser console for any errors
                try {
                    Object logs = DriverManager.get().executeScript(
                        "return window.console.errors || 'No errors captured';"
                    );
                    System.out.println("Browser console status: " + logs);
                } catch (Exception e) {
                    // Ignore if console check fails
                }
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

    @And("the database should reflect the updated {string} as {string}")
    public void theDatabaseShouldReflectTheUpdated(String fieldName, String expectedValue) {
        // Ensure user ID is loaded from localStorage
        ensureUserIdLoaded();
        
        // Get fresh token
        String token = tokenManager.getUserToken();
        
        // Fetch user data from backend API to verify database update
        Response response = userService.getRequest("/api/users/" + currentUserId, token);
        
        Assert.assertEquals(response.getStatusCode(), 200, 
            "Failed to fetch user data from backend. Status: " + response.getStatusCode());
        
        String actualValue;
        switch (fieldName.toLowerCase()) {
            case "full name":
                actualValue = response.jsonPath().getString("name");
                break;
            case "phone number":
                actualValue = response.jsonPath().getString("phone");
                break;
            default:
                throw new IllegalArgumentException("Unsupported field for database verification: " + fieldName);
        }
        
        Assert.assertEquals(actualValue, expectedValue, 
            "Database verification failed! " + fieldName + " in database doesn't match expected value. " +
            "Expected: " + expectedValue + ", Actual in DB: " + actualValue);
        
        System.out.println("✓ Database verification passed: " + fieldName + " = " + actualValue);
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

    @When("I refresh the page")
    public void iRefreshThePage() throws InterruptedException {
        DriverManager.get().navigate().refresh();
        Thread.sleep(2000); // Wait for page to reload
    }
}