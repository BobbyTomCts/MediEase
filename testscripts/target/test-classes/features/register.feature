@smoke
Feature: User Registration

  As a new MediEase user
  I want to register with valid details
  So that I can log in to the application

  Scenario: Successful registration of multiple users redirects to login
    Given I am on the Registration page
    When I register the following new users:
      | name      | phone      | email                | password |
      | Archana   | 2354235423 | haripriya4@gmail.com | selenium |
      | Rajoritha | 9876543210 | haripriya5@gmail.com | selenium |
      | Priya     | 1234567890 | haripriya6@gmail.com | selenium |
    Then I should be redirected to "http://localhost:4200/login" page

  Scenario: Registration fails when attempting to use an existing email
    Given I am on the Registration page
    When I attempt to register with an existing email "kavitha@gmail.com" and password "password123"
    Then I should see the registration error message "User account already exists with this email"
    And I should remain on the Registration page