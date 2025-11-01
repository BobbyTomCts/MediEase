Feature: Network Hospitals Page Functionality

  As a user,
  I want to view, search, and filter the list of network hospitals,
  So that I can quickly find a suitable hospital for a claim.

  Scenario: Navigate to Network Hospitals using Hospitals button
    Given I am on the User Dashboard page
    When I click the "Hospitals" button on the navigation bar
    Then I should be navigated to the page with URL path "http://localhost:4200/network-hospitals"

  Scenario: Return to Dashboard from Hospitals using back-btn
    Given I am on the User Dashboard page
    When I click the "Hospitals" button on the navigation bar
    And I click the back-btn button
    Then I should be navigated to the page with URL path "http://localhost:4200/user-dashboard"
  @smoke
  Scenario: Initial load displays the correct total hospital count
    Given I am on the User Dashboard page
    When I click the "Hospitals" button on the navigation bar
    And the page has successfully loaded 20 hospitals
    Then the hospital count header should display "20 hospitals"
