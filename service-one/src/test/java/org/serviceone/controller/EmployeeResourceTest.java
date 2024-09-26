package org.serviceone.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeResourceTest {
    public static String id;
    public static String departmentId;

    @Order(1)
    @Test
    void createNewDepartment() {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("departmentId" , String.valueOf(new ObjectId()))
                .add("departmentName", "Web")
                .build();

        departmentId = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .post("/department")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .extract().path("departmentId");

    }

    @Order(2)
    @Test
    void createNewEmployee() {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("id" , String.valueOf(new ObjectId()))
                .add("name", "Alice")
                .add("email", "alice@test.com")
                .add("salary", 123456.00)
                .add("departmentId", departmentId)
                .build();

        id = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .post("/employee")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .extract().path("id");

    }

    @Order(3)
    @Test
    void getAllEmployees() {
        RestAssured.given()
                .when()
                .get("/employee")
                .then()
                .body("name", hasItem("Alice"))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Order(4)
    @Test
    void updateEmployee() {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("email", "alice@email.com")
                .build();
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("id", id)
                .body(jsonObject.toString())
                .when()
                .put("/employee/{id}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Order(5)
    @Test
    void testGetAllEmployees() {
        RestAssured.given()
                .when()
                .get("/employee")
                .then()
                .body("email", hasItem("alice@email.com"))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Order(6)
    @Test
    void deleteEmployee() {
        RestAssured.given()
                .pathParam("id", id)
                .when()
                .delete("/employee/{id}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }
}
