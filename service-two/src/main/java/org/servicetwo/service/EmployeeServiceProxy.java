package org.servicetwo.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.servicetwo.entity.Employee;

@Path("/employee")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "serviceone")
public interface EmployeeServiceProxy {

    @POST
    Uni<Response> createNewEmployee(Employee employee);

    @GET
    Uni<Response> getAllEmployees();

    @PUT
    @Path("/{id}")
    Uni<Response> updateEmployee(@PathParam("id") String id, Employee employee);

    @DELETE
    @Path("/{id}")
    Uni<Response> deleteEmployee(@PathParam("id") String id);
}
