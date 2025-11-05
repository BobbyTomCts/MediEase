package org.example.restapi.services;

import io.restassured.response.Response;
import org.example.restapi.Test.BaseTest;

public class RequestService extends BaseTest {

    private static final String REQUESTS_BASE_ENDPOINT = "/api/requests";

//     POST /api/requests/create (Uses Query Params)
    public Response createRequest(Long empId, Double amount, Long hospitalId) {
        return doRequestWithQueryParams(REQUESTS_BASE_ENDPOINT + "/create", empId, amount, hospitalId);
    }

    public Response createRequest(Long empId, Double amount, Long hospitalId, String token) {
        return doRequestWithQueryParams(REQUESTS_BASE_ENDPOINT + "/create", empId, amount, hospitalId, token);
    }

//  GET /api/requests/all
    public Response getAllRequests() {
        return doGet(REQUESTS_BASE_ENDPOINT + "/all");
    }

    public Response getAllRequests(String token) {
        return doGet(REQUESTS_BASE_ENDPOINT + "/all", token);
    }

//    GET /api/requests/filtered (Uses Optional Query Params)
    public Response getFilteredRequests(String status, String startDate, String endDate) {
        return doGetWithQueryParams(REQUESTS_BASE_ENDPOINT + "/filtered", status, startDate, endDate);
    }

    public Response getFilteredRequests(String status, String startDate, String endDate, String token) {
        return doGetWithQueryParams(REQUESTS_BASE_ENDPOINT + "/filtered", status, startDate, endDate, token);
    }

//  GET /api/requests/employee/{empId}
    public Response getRequestsByEmpId(Long empId) {
        return doGet(REQUESTS_BASE_ENDPOINT + "/employee/" + empId);
    }

    public Response getRequestsByEmpId(Long empId, String token) {
        return doGet(REQUESTS_BASE_ENDPOINT + "/employee/" + empId, token);
    }

//     GET /api/requests/status/{status}
    public Response getRequestsByStatus(String status) {
        return doGet(REQUESTS_BASE_ENDPOINT + "/status/" + status);
    }

    public Response getRequestsByStatus(String status, String token) {
        return doGet(REQUESTS_BASE_ENDPOINT + "/status/" + status, token);
    }

//   PUT /api/requests/approve/{requestId} (No body required)
    public Response approveRequest(Long requestId, String token) {
        return doPut(REQUESTS_BASE_ENDPOINT + "/approve/" + requestId, null, token);
    }

//    PUT /api/requests/reject/{requestId} (No body required)
    public Response rejectRequest(Long requestId, String token) {
        return doPut(REQUESTS_BASE_ENDPOINT + "/reject/" + requestId, null, token);
    }
}

