package org.servicetwo.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Order;
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

    @Test
    void createNewEmployee() {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("id" , String.valueOf("111111"))
                .add("name", "Alice")
                .add("email", "alice@test.com")
                .add("salary", 123456.00)
                .add("departmentId", "11111111")
                .build();

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .post("/employee")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());

    }
    @Test
    void updateEmployee() {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("email", "alice@email.com")
                .build();
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("id", 111111111)
                .body(jsonObject.toString())
                .when()
                .put("/employee/{id}")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    void deleteEmployee() {
        RestAssured.given()
                .pathParam("id", 111111111)
                .when()
                .delete("/employee/{id}")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

}