@RequestAPI
Feature: Claim Request Management Endpoints
  Scenario: 1. Approve Request Flow
    Given the admin is logged in with email "admin@gmail.com" and password "admin123"

    # Create a request to approve
    Given an employee creates a new request with details:
      | empId | amount  | hospitalId |
      | 41    | 1000.00 | 12         |
    Then the request is created with PENDING status

#   Approve the request using the admin token
    When the admin approves the created request
    Then the request status is updated to "APPROVED"

  Scenario: 2. Create and Reject Request Flow
    Given the admin is logged in with email "admin@gmail.com" and password "admin123"

#   Create a fresh request for rejection
    Given an employee creates a new request with details:
      | empId | amount  | hospitalId |
      | 41    | 2500.00 | 12         |
    Then the request is created with PENDING status

# 3. Reject the request using the admin token
    When the admin rejects the created request
    Then the request status is updated to "REJECTED"

  Scenario: Retrieve All Requests (/api/requests/all)
    When the user gets all requests
    Then the request response status code is 200
    And the response list is not empty

  Scenario: Retrieve Requests by Employee ID (/api/requests/employee/{empId})
    When the user gets requests for employee ID 41
    Then the request response status code is 200
    And the response list is not empty

  Scenario: Retrieve Requests by Status (/api/requests/status/{status})
#   Ensure there's a PENDING request to find
    Given an employee creates a new request with details:
      | empId | amount  | hospitalId |
      | 41    | 1000.00 | 12         |

    When the user gets requests filtered by status "PENDING"
    Then the request response status code is 200
    And the response list is not empty
    And all requests in the response have status "PENDING"


  Scenario: 6. Retrieve Filtered Requests (Date Range)
# NOTE: The date range must encompass the time of test execution.
    Given the user gets requests filtered by status "PENDING" and date range from "2023-01-01T00:00:00" to "2025-12-31T23:59:59"
    Then the request response status code is 200
    And the response list is not empty
    And all requests in the response have status "PENDING"