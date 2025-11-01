Feature: Insurance Package Selection

  As a user who has logged in,
  I want to select an insurance package and proceed to Dependents page.

  Background: User Login and Initial State Setup
    Given I navigate to the login page
    When I log in with email "vineesha1@gmail.com" and password "selenium"
    Then I should be on the Insurance Selection page
  @smoke
  Scenario: Successful Login and Welcome Message Display
    Then the welcome message should start with "Welcome,"

  Scenario: Initial State: Proceed Button is Disabled
    Then the "Proceed to Dependents" button should be disabled

  Scenario: Package Selection Enables Proceed Button
    When I select the "Basic" insurance package
    Then the "Proceed to Dependents" button should be enabled

  Scenario: Successful Navigation to Dependents Management
    When I select the "Basic" insurance package
    Then the "Proceed to Dependents" button should be enabled
    When I click the "Proceed to Dependents" button
    Then I should be navigated to the Dependents Management page

