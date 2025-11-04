Feature: User Profile Functionality

  As a logged-in user,
  I want to view and manage my profile details,
  So that my personal information is accurate and I can manage my dependants.

  Background: User logs in and navigates to Profile
    Given I am on the User Dashboard page
    When I click the "Profile" button on the navigation bar
    Then I should be navigated to the page with URL path "http://localhost:4200/user-dashboard"
    And I should see the profile title as "My Profile"

  Scenario: 1. Successful Profile Editing (Name and Phone)

    When I click "Edit Profile" button
    And I set the "Full Name" to "CH Kavitha"
    And I set the "Phone Number" to "9988776655"
    And I click "Save Changes" button
    Then I should see the success message "Profile updated successfully!"
    And I should see the "Full Name" displayed as "CH Kavitha"
    And I should see the "Phone Number" displayed as "9988776655"
    When I refresh the page
    Then I should see the "Full Name" displayed as "CH Kavitha"
    And I should see the "Phone Number" displayed as "9988776655"

  Scenario: 2. Cancel Profile Editing

    When I click "Edit Profile" button
    And I set the "Full Name" to "Chitteti"
    And I click "Cancel" button
    Then I should see the "Edit Profile" button
    And I should see the "Full Name" displayed as "Chitteti Kavitha"

  @smoke
  Scenario: 3. Navigation to Manage Dependents

    When I click "Manage Dependants" button
    Then I should be navigated to the page "http://localhost:4200/dependants-management"
