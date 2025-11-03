package org.example.restapi.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.restapi.models.User;

public class BaseTest {
    RequestSpecification requestSpecification;

    public BaseTest() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecification = requestSpecBuilder
                .setBaseUri("http://localhost:8082")
                .setContentType(ContentType.JSON)
                .build();
    }

    protected Response doGet(String endpoint) {
        return RestAssured
                .given(requestSpecification)
                .when()
                .get(endpoint)
                .then().extract().response();
    }

    protected Response doPost1(String endpoint, User body) {
        return RestAssured
                .given(requestSpecification)
                .queryParam("email", body.getEmail())
                .queryParam("password", body.getPassword())
                .auth().none()
                .when()
                .post(endpoint)
                .then().extract().response();
    }

    protected Response doPost(String endpoint, Object body) {
        return RestAssured
                .given(requestSpecification)
                .auth().none()
                .body(body)
                .when()
                .post(endpoint)
                .then().extract().response();
    }

    protected Response doGet(String endpoint, String token) {
        return RestAssured
                .given(requestSpecification)
                .header("Authorization", "Bearer " + token)
                .when()
                .get(endpoint)
                .then().extract().response();
    }

    protected Response doPost(String endpoint, Object body, String token) {
        return RestAssured
                .given(requestSpecification)
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .post(endpoint)
                .then().extract().response();
    }

    protected Response doDelete(String endpoint) {
        return RestAssured
                .given(requestSpecification)
                .when()
                .delete(endpoint)
                .then().extract().response();
    }

    // doPut allows null body for PUT requests without body (like approve/reject)
    protected Response doPut(String endpoint, Object body, String token) {
        RequestSpecification spec = RestAssured.given(requestSpecification);
        if (token != null) {
            spec.header("Authorization", "Bearer " + token);
        }
        if (body != null) {
            spec.body(body);
        }
        return spec.when()
                .put(endpoint)
                .then().extract().response();
    }
//  Dedicated method for POST requests that use Query Parameters (/requests/create).
    protected Response doRequestWithQueryParams(String endpoint, Long empId, Double amount, Long hospitalId) {
        return RestAssured
                .given(requestSpecification)
                .queryParam("empId", empId)
                .queryParam("amount", amount)
                .queryParam("hospitalId", hospitalId)
                .when()
                .post(endpoint)
                .then().extract().response();
    }
    protected Response doRequestWithQueryParams2(String endpoint, Long empId, Long packageId) {
        return RestAssured
                .given(requestSpecification)
                .queryParam("empId", empId)
                .queryParam("packageId", packageId)
                .when()
                .post(endpoint)
                .then().extract().response();
    }

    protected Response doRequestWithQueryParams1(String endpoint,Long empId, String name, Integer age, String gender, String relation)
    {
        return RestAssured
                .given(requestSpecification)
                .queryParam("empId", empId)
                .queryParam("name", name)
                .queryParam("age", age)
                .queryParam("gender", gender)
                .queryParam("relation", relation)
                .when()
                .post(endpoint)
                .then().extract().response();

    }

    protected Response doRequestWithQueryParams2(String endpoint, String name, Integer age, String gender, String relation)
    {
        return RestAssured
                .given(requestSpecification)
                .queryParam("name", name)
                .queryParam("age", age)
                .queryParam("gender", gender)
                .queryParam("relation", relation)
                .when()
                .put(endpoint)
                .then().extract().response();

    }

//     Dedicated method for GET requests with Optional Query Parameters (e.g., /requests/filtered).
    protected Response doGetWithQueryParams(String endpoint, String status, String startDate, String endDate) {
        RequestSpecification spec = RestAssured.given(requestSpecification);
        if (status != null) { spec.queryParam("status", status); }
        if (startDate != null) { spec.queryParam("startDate", startDate); }
        if (endDate != null) { spec.queryParam("endDate", endDate); }

        return spec.when()
                .get(endpoint)
                .then().extract().response();
    }
}
