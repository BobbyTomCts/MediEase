package org.example.restapi.services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.restapi.Test.BaseTest;
import org.example.restapi.models.Dependant;

public class InsuranceApiService extends BaseTest {
    private static final String BASE_ENDPOINT = "/api/insurance";

    // 1. GET /api/insurance/packages
    public Response getAllPackages() {
        return doGet(BASE_ENDPOINT + "/packages");
    }

    // 2. POST /api/insurance/create?empId={empId}&packageId={packageId}
    public Response createInsurance(Long empId, Long packageId) {
        return doRequestWithQueryParams2(BASE_ENDPOINT + "/create", empId, packageId);
    }

    // 3. POST /api/insurance/dependant/add?empId={empId}&name={name}&age={age}&gender={gender}&relation={relation}
    public Response addDependant(Long empId, String name, Integer age, String gender, String relation) {
        return doRequestWithQueryParams1(BASE_ENDPOINT + "/dependant/add",empId,name,age,gender,relation);
    }

    // 4. GET /api/insurance/dependants/{empId}
    public Response getDependants(Long empId) {
        return doGet(BASE_ENDPOINT + "/dependants/" + empId);
    }

    // 5. PUT /api/insurance/dependant/update/{dependantId}?name={name}&age={age}&gender={gender}&relation={relation}
    public Response updateDependant(Long dependantId, String name, Integer age, String gender, String relation) {
        return doRequestWithQueryParams2(BASE_ENDPOINT + "/dependant/update/" + dependantId,name,age,gender,relation);
    }

    // 6. DELETE /api/insurance/dependant/delete/{dependantId}
    public Response deleteDependant(Long dependantId) {
        return doDelete(BASE_ENDPOINT + "/dependant/delete/" + dependantId);
    }

    // 7. GET /api/insurance/{empId}
    public Response getInsuranceByEmpId(Long empId) {
        return doGet(BASE_ENDPOINT + "/" + empId);
    }
}
