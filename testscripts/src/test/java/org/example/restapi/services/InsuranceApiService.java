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

    public Response getAllPackages(String token) {
        return doGet(BASE_ENDPOINT + "/packages", token);
    }

    // 2. POST /api/insurance/create?empId={empId}&packageId={packageId}
    public Response createInsurance(Long empId, Long packageId) {
        return doRequestWithQueryParams2(BASE_ENDPOINT + "/create", empId, packageId);
    }

    public Response createInsurance(Long empId, Long packageId, String token) {
        return doRequestWithQueryParams2(BASE_ENDPOINT + "/create", empId, packageId, token);
    }

    // 3. POST /api/insurance/dependant/add?empId={empId}&name={name}&age={age}&gender={gender}&relation={relation}
    public Response addDependant(Long empId, String name, Integer age, String gender, String relation) {
        return doRequestWithQueryParams1(BASE_ENDPOINT + "/dependant/add",empId,name,age,gender,relation);
    }

    public Response addDependant(Long empId, String name, Integer age, String gender, String relation, String token) {
        return doRequestWithQueryParams1(BASE_ENDPOINT + "/dependant/add",empId,name,age,gender,relation,token);
    }

    // 4. GET /api/insurance/dependants/{empId}
    public Response getDependants(Long empId) {
        return doGet(BASE_ENDPOINT + "/dependants/" + empId);
    }

    public Response getDependants(Long empId, String token) {
        return doGet(BASE_ENDPOINT + "/dependants/" + empId, token);
    }

    // 5. PUT /api/insurance/dependant/update/{dependantId}?name={name}&age={age}&gender={gender}&relation={relation}
    public Response updateDependant(Long dependantId, String name, Integer age, String gender, String relation) {
        return doRequestWithQueryParams2(BASE_ENDPOINT + "/dependant/update/" + dependantId,name,age,gender,relation);
    }

    public Response updateDependant(Long dependantId, String name, Integer age, String gender, String relation, String token) {
        return doRequestWithQueryParams2(BASE_ENDPOINT + "/dependant/update/" + dependantId,name,age,gender,relation,token);
    }

    // 6. DELETE /api/insurance/dependant/delete/{dependantId}
    public Response deleteDependant(Long dependantId) {
        return doDelete(BASE_ENDPOINT + "/dependant/delete/" + dependantId);
    }

    public Response deleteDependant(Long dependantId, String token) {
        return doDelete(BASE_ENDPOINT + "/dependant/delete/" + dependantId, token);
    }

    // 7. GET /api/insurance/{empId}
    public Response getInsuranceByEmpId(Long empId) {
        return doGet(BASE_ENDPOINT + "/" + empId);
    }

    public Response getInsuranceByEmpId(Long empId, String token) {
        return doGet(BASE_ENDPOINT + "/" + empId, token);
    }
}
