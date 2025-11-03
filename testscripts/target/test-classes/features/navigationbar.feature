@smoke
Feature: Navigation Bar Tests

  As a user of the application
  I want to verify the navigation bar links
  So that I can move between core pages reliably

  Scenario: 1. Navigate to User Dashboard using Home button
    Given I am on the User Dashboard page
    When I click the "Home" button on the navigation bar
    Then I should be navigated to the page with URL path "http://localhost:4200/user-dashboard"

  Scenario: 2. Navigate to Network Hospitals using Hospitals button
    Given I am on the User Dashboard page
    When I click the "Hospitals" button on the navigation bar
    Then I should be navigated to the page with URL path "http://localhost:4200/network-hospitals"

  Scenario: 3. Return to Dashboard from Hospitals using back-btn
    Given I am on the User Dashboard page
    When I click the "Hospitals" button on the navigation bar
    And I click the back-btn button
    Then I should be navigated to the page with URL path "http://localhost:4200/user-dashboard"

  Scenario: 4. Navigate to Profile view and verify header content
    Given I am on the User Dashboard page
    When I click the "Profile" button on the navigation bar
    Then I should be navigated to the page with URL path "http://localhost:4200/user-dashboard"
    And I should see the profile title as "My Profile"

  Scenario: 5. Navigate to Login page using Logout button
    Given I am on the User Dashboard page
    When I click the "Logout" button on the navigation bar
    Then I should be navigated to the page with URL path "http://localhost:4200/login"