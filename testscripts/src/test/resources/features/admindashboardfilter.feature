@regression
Feature: Admin Dashboard Filtering

  As an Admin,
  I want to filter the request table by status and date, and clear filters,
  So I can efficiently manage claims.

  Scenario: Filter Requests by Status (REJECTED)
    Given I am on the Admin page
    When I select the status filter "REJECTED"
    Then the table should only show requests with status "REJECTED"

  Scenario: Filter Requests by Status (APPROVED)
    Given I am on the Admin page
    When I select the status filter "APPROVED"
    Then the table should only show requests with status "APPROVED"

  Scenario: Filter Requests by Status (PENDING)
    Given I am on the Admin page
    When I select the status filter "PENDING"
    Then the table should only show requests with status "PENDING"

  Scenario: Filter Requests by Date Range
    Given I am on the Admin page
    When I filter requests from date "01-10-2025" to date "31-10-2025"
    Then the table should only show requests within the date range "01-10-2025" to "31-10-2025"

  Scenario: Clear Filters Button Functionality
    Given I am on the Admin page
    When I select the status filter "APPROVED"
    And I filter requests from date "01-10-2025" to date "31-10-2025"
    When I click the Clear Filters button
    Then the status filter should be reset to "ALL"
    And the date filters should be cleared