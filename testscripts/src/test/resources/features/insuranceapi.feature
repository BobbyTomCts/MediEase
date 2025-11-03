Feature: Insurance Module API Testing

  @GetPackages
  Scenario: Get all available insurance packages
    When a GET request is sent to the "/packages"
    Then the response status code should be 200
    And the response body should be a list of InsuranceType packages
#
  @CreateInsurance
  Scenario: Create insurance for an existing employee
    When a POST request is sent to "/create" with empId 56 and packageId 4
    Then the response status code should be 200
    And the response contains an Insurance object for empId 56
    And the Insurance object "packageName" is not null

#  @GetInsurance
#  Scenario: Get insurance details for a specific employee
#    Given Employee with ID 57 has existing insurance
#    When a GET request is sent to 57
#    Then the response status code should be 200
#    And the response Insurance object "empId" is 57

  @AddDependant
  Scenario: Add a new dependant to an employee
    When a POST request is sent to "/dependant/add" for empId 56 with name "Janu", age 10, gender "FEMALE", and relation "CHILD"
    Then the response status code should be 200
    And the response Dependant object "name" is "Janu"

  @GetDependants
  Scenario: Get all dependants for a specific employee
    Given Employee with ID 56 has dependants
    When a GET request is sent to 56
    Then the response status code should be 200
    And the response body should be a list of Dependant objects
    And the list size should be greater than 0

  @UpdateDependant
  Scenario: Update an existing dependant's details
    Given A dependant with ID 46 exists for empId 56
    When a PUT request is sent to "/dependant/update/46" with name "Jane Doe", age 10, gender "FEMALE", and relation "CHILD"
    Then the response status code should be 200
    And the response Dependant object "name" is "Jane Doe"
    And the response Dependant object "age" is 10

  @DeleteDependant
  Scenario: Delete an existing dependant
    Given A dependant with ID 46 exists and needs to be deleted
    When a DELETE request is sent to "/dependant/delete/46"
    Then the response status code should be 200
    And the response message is "Dependant deleted successfully"