@regression
Feature: Insurance Package Selection Complete Setup
  As a user who has logged in for the first time
  I want to select an insurance package
  and add the dependents.
  @e2e
  Scenario: Skip Dependents and Complete Setup
    Given I navigate to the login page
    When I log in with email "efgh@gmail.com" and password "rajoritha123"
    Then I should be on the Insurance Selection page
    Given I select the "Basic" insurance package
    And I click the "Proceed to Dependents" button
    Then "Skip & Complete Setup" button should be enabled
    When I click on "Skip & Complete Setup" button
    Then I should be navigated to the User Dashboard
  @e2e
  Scenario: Add One Dependant and Complete Setup
    Given I navigate to the login page
    When I log in with email "ijkl@gmail.com" and password "priya123"
    Then I should be on the Insurance Selection page
    When I select the "Basic" insurance package
    And I click the "Proceed to Dependents" button
    When I click on "Add Dependant" button
    Then the "Add Dependant" form should be visible
    When I fill the dependant form with name "Lalitha" age 24 gender "Female" and relation "Child" and click submit
    Then "Complete Setup" button should be enabled
    When I click on "Complete Setup" button
    Then I should be navigated to the User Dashboard
