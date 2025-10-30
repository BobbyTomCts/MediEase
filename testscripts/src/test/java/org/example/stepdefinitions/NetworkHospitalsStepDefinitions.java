package org.example.stepdefinitions;

import io.cucumber.java.en.*;
import org.example.pages.NetworkHospitalsPage;
import org.testng.Assert;

public class NetworkHospitalsStepDefinitions {

    private NetworkHospitalsPage hospitalsPage;

    public NetworkHospitalsStepDefinitions(){
        this.hospitalsPage = new NetworkHospitalsPage();
    }

    @Given("the page has successfully loaded {int} hospitals")
    public void thePageHasSuccessfullyLoadedHospitals(int expectedCount) {
        Assert.assertEquals(hospitalsPage.getDisplayedHospitalCount(), expectedCount,
                "Initial displayed hospital count does not match expected loaded count.");
    }

    @Then("the hospital count header should display {string}")
    public void theHospitalCountHeaderShouldDisplay(String expectedText) {
        Assert.assertEquals(hospitalsPage.getHospitalCountText(), expectedText,
                "Hospital count header is incorrect.");
    }

    @When("I search for {string}")
    public void iSearchFor(String searchTerm) {
        hospitalsPage.enterSearchTerm(searchTerm);
    }
//
//    @When("I filter by State {string}")
//    public void iFilterByState(String state) {
//        hospitalsPage.selectState(state);
//    }
//
//    @When("I click the {string} button")
//    public void iClickTheButton(String buttonText) {
//        if (buttonText.equals("Clear Filters")) {
//            hospitalsPage.clickClearFilters();
//        } else {
//            Assert.fail("Button " + buttonText + " is not supported in this step definition.");
//        }
//    }
//
//    @Given("I have searched for {string} and filtered by State {string}")
//    public void iHaveSearchedForAndFilteredByState(String searchTerm, String state) {
//        hospitalsPage.enterSearchTerm(searchTerm);
//        hospitalsPage.selectState(state);
//    }
//
//


    @Then("I should see only {int} hospital in the list")
    public void iShouldSeeOnlyHospitalInTheList(int expectedCount) {
        Assert.assertEquals(hospitalsPage.getDisplayedHospitalCount(), expectedCount,
                "Number of displayed hospitals is incorrect.");
    }

    @Then("the hospital name {string} should be visible")
    public void theHospitalNameShouldBeVisible(String hospitalName) {
        Assert.assertTrue(hospitalsPage.isHospitalNameVisible(hospitalName),
                "Hospital with name " + hospitalName + " is not visible.");
    }

    @And("the hospital list contains {string} in Chennai")
    public void theHospitalListContainsInChennai(String hospitalName) {
    }

//    @Then("all displayed hospitals should be located in {string}")
//    public void allDisplayedHospitalsShouldBeLocatedIn(String stateOrCity) {
//        // This requires iterating through the list of cards, which is complex
//        // to fully automate without a helper function.
//        // For now, we rely on the count and specific name check.
//        // NOTE: In a complete implementation, we'd iterate over hospitalCards
//        // and check the location text within each card.
//        System.out.println("Verification of all displayed locations in " + stateOrCity + " is assumed successful for demonstration.");
//    }
//
//    @Then("the search box should be empty")
//    public void theSearchBoxShouldBeEmpty() {
//        Assert.assertTrue(hospitalsPage.getSearchInputValue().isEmpty(),
//                "Search input field was not cleared.");
//    }
//
//    @Then("the State filter should be set to {string}")
//    public void theStateFilterShouldBeSetTo(String expectedValue) {
//        Assert.assertEquals(hospitalsPage.getSelectedStateValue(), expectedValue,
//                "State filter was not reset to " + expectedValue);
//    }
//
//    @Then("the City filter should be set to {string}")
//    public void theCityFilterShouldBeSetTo(String expectedValue) {
//        Assert.assertEquals(hospitalsPage.getSelectedCityValue(), expectedValue,
//                "City filter was not reset to " + expectedValue);
//    }
//
//    @Then("the displayed hospital count should revert to the initial total")
//    public void theDisplayedHospitalCountShouldRevertToTheInitialTotal() {
//        // This step requires storing the initial count in a scenario context.
//        // For simple test demonstration, we'll check that the filters are reset.
//        System.out.println("Assuming count reversion is covered by filter resets and initial count check.");
//    }
//
//    @Then("the {string} message should be displayed")
//    public void theNoHospitalsFoundMessageShouldBeDisplayed(String message) {
//        Assert.assertTrue(hospitalsPage.isNoResultsMessageDisplayed(),
//                "The 'No results found' message is not displayed.");
//    }
}
