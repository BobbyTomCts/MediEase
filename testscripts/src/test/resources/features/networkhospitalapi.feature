@api_networkhospital
Feature: Network Hospital API Test

  As a user or admin,
  I want to search and retrieve network hospital details,
  So I can find the best facility for a claim.

  Scenario: 1. Retrieve All Hospitals Successfully

    When the user retrieves all network hospitals
    Then the hospital response status code is 200
    And the hospital response list is not empty

  Scenario: 2. Search Hospitals by City (Positive Case)

    When the user searches for hospitals by city "Chennai"
    Then the hospital response status code is 200
    And the hospital response list is not empty
    And all hospitals in the response are in the city "Chennai"

  Scenario: 3. Search Hospitals by City (No Results)

    When the user searches for hospitals by city "Gondor"
    Then the hospital response status code is 200
    And the hospital response list is empty

  Scenario: 4. Search Hospitals by State (Positive Case)

    When the user searches for hospitals by state "Tamil Nadu"
    Then the hospital response status code is 200
    And the hospital response list is not empty
    And all hospitals in the response are in the state "Tamil Nadu"

  Scenario: 5. Retrieve Hospital by ID (Positive Case)

    When the user retrieves the hospital with ID 1
    Then the hospital response status code is 200
    And the hospital name in the response is "Apollo Hospital"
    And the hospital has a copay percentage of 10.0

  Scenario: 6. Retrieve Hospital by ID (Not Found)

    When the user retrieves the hospital with ID 9999
    Then the hospital response status code is 404