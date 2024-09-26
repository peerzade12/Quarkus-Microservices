package org.servicetwo.controller;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.servicetwo.entity.Employee;
import org.servicetwo.exception.CustomArithmeticException;
import org.servicetwo.service.EmployeeServiceProxy;

import java.time.temporal.ChronoUnit;

@Path("/employ")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeServiceResource {

    @Inject
    @RestClient
    EmployeeServiceProxy employeeServiceProxy;

    @POST
    @Retry(maxRetries = 2)
    public Uni<Response> createEmployee(Employee employee) {
        if (employee.getDepartmentId() == null || employee.getDepartmentId().length() != 24) {
            return Uni.createFrom().failure(
                    new CustomArithmeticException(Response.Status.BAD_REQUEST.getStatusCode(), "Department ID must be a 24-character hex string. Provided ID: " + employee.getDepartmentId()));
        }
        return employeeServiceProxy.createNewEmployee(employee)
                .onFailure()
                .transform(err -> new CustomArithmeticException(Response.Status.NOT_FOUND.getStatusCode(), "The department id not found in record."));
    }

    @GET
    @Retry(maxRetries = 10)
    @Timeout(value = 15, unit = ChronoUnit.SECONDS)
    @Fallback(fallbackMethod = "handleConnectionRefusedFallback")
    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.6, delay = 20, delayUnit = ChronoUnit.SECONDS)
    public Uni<Response> getAllEmployees() {
        return employeeServiceProxy.getAllEmployees();
    }

    @PUT
    @Path("/{id}")
    @Retry(maxRetries = 2)
    @Timeout(value = 15, unit = ChronoUnit.SECONDS)
    public Uni<Response> updateEmployee(@PathParam("id") String id, Employee employee) {
        return employeeServiceProxy.updateEmployee(id, employee)
                .onFailure()
                .transform(err -> new CustomArithmeticException(Response.Status.NOT_FOUND.getStatusCode(), "The employee id not found in record."));
    }

    @DELETE
    @Path("/{id}")
    @Retry(maxRetries = 2)
    public Uni<Response> deleteEmployee(@PathParam("id") String id) {
        return employeeServiceProxy.deleteEmployee(id)
                .onFailure()
                .transform(err -> new CustomArithmeticException(Response.Status.NOT_FOUND.getStatusCode(), "The employee "+id+" id not found in record."));
    }

    public Uni<Response> handleConnectionRefusedFallback() {
        return Uni.createFrom()
                .item(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity("Connection Refused : server down.")
                        .build()
                );
    }
}
