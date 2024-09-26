package org.servicetwo.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class EmployeeServiceResourceTest {

    @Test
    void getAllEmployees() {
        RestAssured.given()
                .when()
                .get("/employ")
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }

}