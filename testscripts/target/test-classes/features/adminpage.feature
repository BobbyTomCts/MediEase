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
    When I click the "Approve" button for Request id "46"
    Then The status for Request Id "46" should change to "APPROVED"

    When I click on logout button
    Then I should be redirected to login page

    Given I login as user with email "lalitha@gmail.com" and password "selenium"
    Then the user sees status "APPROVED" for Request Id "46" on their dashboard

  Scenario: Rejection for the pending claim and claim rejection verification in user dashboard
    Given I am on the Admin page
    When I click the "Reject" button for Request id "49"
    Then The status for Request Id "49" should change to "REJECTED"

    When I click on logout button
    Then I should be redirected to login page

    Given I login as user with email "lalitha@gmail.com" and password "selenium"
    Then the user sees status "REJECTED" for Request Id "49" on their dashboard