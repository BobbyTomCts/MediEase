@smoke @regression
Feature: User Authentication

  As a MediEase user
  I want to be able to log in
  So I can access my account dashboard

  Scenario Outline: Successful User Login Redirect (Data Driven)
    Given I am on the Login page
    When I log in with valid credentials "<email>" and "<password>"
    Then I should be redirected to the dashboard or insurance selection page

    Examples: Valid User Credentials
      | email               | password   |
      | jeni@gmail.com      | jeni123    |
      | archa@gmail.com     | archana123 |
      | pri@gmail.com       | priya123   |

  Scenario: Successful Admin Login
    Given I am on the Login page
    When I log in with valid credentials "admin@gmail.com" and "admin123"
    Then I should be redirected to the admin dashboard

  Scenario: Navigate to Registration Page
    Given I am on the Login page
    When I click on the "Create Account" link
    Then I should be redirected to the "http://localhost:4200/register" page

  Scenario: Invalid User Login (Password)
    Given I am on the Login page
    When I log in with valid credentials "suba@example.com" and "password"
    Then I should see the invalid password error message

  Scenario: Invalid User Login (User Not Found)
    Given I am on the Login page
    When I log in with valid credentials "nonexistent@example.com" and "password"
    Then I should see the user not found error message


