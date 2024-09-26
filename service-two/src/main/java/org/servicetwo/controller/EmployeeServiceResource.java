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
    @Fallback(fallbackMethod = "handleConnectionFallback")
    public Uni<Response> createEmployee(Employee employee) {
        return employeeServiceProxy.createNewEmployee(employee);
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
    @Fallback(fallbackMethod = "serverErrorFallback")
    public Uni<Response> updateEmployee(@PathParam("id") String id, Employee employee) {
        return employeeServiceProxy.updateEmployee(id, employee);
    }

    @DELETE
    @Path("/{id}")
    @Retry(maxRetries = 2)
    @Fallback(fallbackMethod = "serviceNotRespondingFallback")
    public Uni<Response> deleteEmployee(@PathParam("id") String id) {
        return employeeServiceProxy.deleteEmployee(id);
    }

    public Uni<Response> handleConnectionRefusedFallback() {
        return Uni.createFrom()
                .item(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity("Connection Refused : server down.")
                        .build()
                );
    }

    public Uni<Response> serverErrorFallback(String id, Employee employee) {
        return Uni.createFrom()
                .item(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Either the server did not respond or the requested data invalid")
                        .build());
    }

    public Uni<Response> serviceNotRespondingFallback(String id) {
        return Uni.createFrom()
                .item(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity("Try again after some time.")
                        .build());
    }

    public Uni<Response> handleConnectionFallback(Employee employee) {
        return Uni.createFrom()
                .item(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity("Try again after some time.")
                        .build());
    }
}
