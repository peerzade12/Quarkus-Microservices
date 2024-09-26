package org.serviceone.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DepartmentResourceTest {
    public static String departmentId;
    @Test
    void getAllDepartment() {
        RestAssured
                .given().when()
                .get("/department")
                .then()
                .statusCode(200);
    }

    @Order(1)
    @Test
    void createNewDepartment() {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("departmentId" , String.valueOf(new ObjectId()))
                .add("departmentName", "Quarkus")
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
    void testGetAllDepartment() {
        RestAssured.given()
                .when()
                .get("/department")
                .then()
                .body("departmentName", hasItem("Quarkus"))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Order(3)
    @Test
    void testDeleteDepartmentById(){
        RestAssured.given()
                .pathParam("id", departmentId)
                .when()
                .delete("/department/{id}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }

}
