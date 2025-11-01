@api_register_and_login
Feature: API

  Scenario: Testing the register and login of the user
    Given user is registering
      | fieldName | value               |
      | name      | vineesha            |
      | phone     | 8778878787          |
      | email     | vineesha2@gmail.com |
      | password  | selenium            |
    When the user is successfully registered
    Then the user can login

    Scenario: Testing login
      Given the user can login with "vineesha@gmail.com" and "selenium"
      Then token is generated successfully



