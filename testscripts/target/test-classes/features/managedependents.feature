@regression
Feature: Manage Dependents Functionality

  As a user who has logged in,
  I want to add, edit or remove my dependents.

  @smoke
  Scenario: 1. Successfully add a new dependent
    Given I navigate to the login page
    When I log in with email "lakshm@gmail.com" and password "lakshmi@123"
    And I click the "Profile" button on the navigation bar
    And I click "Manage Dependants" button
    Then I should be navigated to the page "http://localhost:4200/dependants-management"
    When I click on "Add Dependant" button
    Then the "Add Dependant" form should be visible
    When I fill the dependant form with name "Lalitha" age 24 gender "Female" and relation "Child" and click submit
    Then the dependent with name "Lalitha" should be displayed on the page


  Scenario: 2. Successfully remove an existing dependent
    Given I navigate to the login page
    When I log in with email "lakshm@gmail.com" and password "lakshmi@123"
    And I click the "Profile" button on the navigation bar
    And I click "Manage Dependants" button
    Then I should be navigated to the page "http://localhost:4200/dependants-management"
    Given the dependent with name "Lalitha" is displayed
    When I click the "Remove" button for dependent "Lalitha"
    And I confirm the removal
    Then the dependent with name "Lalitha" and relation "Child" should not be displayed on the page