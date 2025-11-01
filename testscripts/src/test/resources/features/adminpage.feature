@regression
Feature: Admin Page Testing
  As a MediEase Admin
  I want to perform the action of claim accept and reject of the user
  So that the user can get claimed insurance
  @smoke
  Scenario: Successful Admin Logout
    Given I am on the Admin page
    When I click on logout button
    Then I should be redirected to login page

  Scenario: Approval for the pending claim and claim approval verification in user dashboard
    Given I am on the Admin page
    When I click the "Approve" button for Request id "42"
    Then The status for Request Id "42" should change to "APPROVED"

    When I click on logout button
    Then I should be redirected to login page

    Given I login as user with email "vineesha@gmail.com" and password "selenium"
    Then the user sees status "APPROVED" for Request Id "42" on their dashboard

  Scenario: Rejection for the pending claim and claim rejection verification in user dashboard
    Given I am on the Admin page
    When I click the "Reject" button for Request id "43"
    Then The status for Request Id "43" should change to "REJECTED"

    When I click on logout button
    Then I should be redirected to login page

    Given I login as user with email "vineesha@gmail.com" and password "selenium"
    Then the user sees status "REJECTED" for Request Id "43" on their dashboard