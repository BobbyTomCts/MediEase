package org.example.restapi.services;

import io.restassured.response.Response;
import org.example.restapi.Test.BaseTest;

public class NetworkHospitalService extends BaseTest {

    private static final String HOSPITALS_BASE_PATH = "/api/hospitals";

//  GET /api/hospitals/all
    public Response getAllHospitals() {
        return doGet(HOSPITALS_BASE_PATH + "/all");
    }

    public Response getAllHospitals(String token) {
        return doGet(HOSPITALS_BASE_PATH + "/all", token);
    }

//  GET /api/hospitals/city/{city}
    public Response getHospitalsByCity(String city) {
        return doGet(HOSPITALS_BASE_PATH + "/city/" + city);
    }

    public Response getHospitalsByCity(String city, String token) {
        return doGet(HOSPITALS_BASE_PATH + "/city/" + city, token);
    }

//    GET /api/hospitals/state/{state}
    public Response getHospitalsByState(String state) {
        return doGet(HOSPITALS_BASE_PATH + "/state/" + state);
    }

    public Response getHospitalsByState(String state, String token) {
        return doGet(HOSPITALS_BASE_PATH + "/state/" + state, token);
    }

//    GET /api/hospitals/{id}

    public Response getHospitalById(Long id) {
        return doGet(HOSPITALS_BASE_PATH + "/" + id);
    }

    public Response getHospitalById(Long id, String token) {
        return doGet(HOSPITALS_BASE_PATH + "/" + id, token);
    }
}
