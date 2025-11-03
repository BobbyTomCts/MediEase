Feature: Claim Submission Functionality

  As a user with an active insurance policy
  I want to submit a new claim
  So that I can get reimbursed for my hospital expenses

  Background: User Login and Initial State Setup
    Given I navigate to the login page
    When I log in with email "lalitha@gmail.com" and password "selenium"
    Then I should be on the User Dashboard page

  @smoke
  Scenario: 1. Verify User Dashboard Access and Welcome Message
    Then I should see the welcome text starting with "Welcome back"
#Checking the status of submit new claim

  Scenario: 2. Successful New Claim Submission and Status Check
    When I click on the 'Submit New Claim' button
    And I fill the claim form with hospital "Fortis Hospital (Mumbai, Maharashtra) - 12% Copay" and amount "20000"
    And I click the 'Submit Claim' button in the popup
    Then the newest claim with requested amount "₹20,000.00" should have status "PENDING"