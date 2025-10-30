package org.example.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.example.core.utils.Config;
import org.example.core.utils.DriverManager;
import org.example.pages.LoginPage;
import org.example.pages.NavigationBar;
import org.testng.Assert;

public class NavigationBarStepDefinitions {

    private NavigationBar navigationBarPage;
    private LoginPage loginPage;


    public NavigationBarStepDefinitions() {
        this.navigationBarPage = new NavigationBar();
        this.loginPage = new LoginPage();
    }


    @Given("I am on the User Dashboard page")
    public void iAmOnTheUserDashboardPage() {
        DriverManager.get().get("http://localhost:4200/login");
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        loginPage.login("kavitha@gmail.com","kavitha123");
    }

    @When("I click the {string} button on the navigation bar")
    public void iClickTheButtonOnTheNavigationBar(String buttonName) {
        switch (buttonName.toLowerCase()) {
            case "home":
                navigationBarPage.clickHomeButton();
                //try { Thread.sleep(2000); } catch (InterruptedException e) {}
                break;
            case "hospitals":
                navigationBarPage.clickHospitalsButton();
                //try { Thread.sleep(2000); } catch (InterruptedException e) {}
                break;
            case "profile":
                navigationBarPage.clickProfileButton();
                //try { Thread.sleep(2000); } catch (InterruptedException e) {}
                break;
            case "logout":
                navigationBarPage.clickLogoutButton();
                //try { Thread.sleep(2000); } catch (InterruptedException e) {}
                break;
            default:
                throw new IllegalArgumentException("Unsupported navigation button: " + buttonName);
        }
    }

    @Then("I should be navigated to the page with URL path {string}")
    public void iShouldBeNavigatedToThePageWithURLPath(String expectedUrl) {
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        String currentUrl = navigationBarPage.getCurrentUrlPath();

        Assert.assertEquals(currentUrl, expectedUrl,
                "The navigation URL is incorrect after clicking. Expected: " + expectedUrl + ", but found: " + currentUrl);

    }




    @When("I click the back-btn button")
    public void iClickTheBackBtnButton() {
        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        navigationBarPage.clickBackButton();
        //System.out.println("Clicked");
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    @Then("I should see the profile title as {string}")
    public void iShouldSeeTheProfileTitleAsMyProfile(String expectedTitle) {
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        String actualTitle = navigationBarPage.getProfileHeaderText().replaceFirst("^[^a-zA-Z0-9]+", "").trim();
        Assert.assertEquals(actualTitle,expectedTitle.trim(),"The navigation to profile page has not occurred.");
    }


}

