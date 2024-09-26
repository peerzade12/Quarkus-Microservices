package org.serviceone.controller;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.serviceone.entity.Employee;
import org.serviceone.service.EmployeeService;

@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    @Inject
    EmployeeService employeeService;

    public static final Logger LOG = Logger.getLogger(BookResource.class);

    @POST
    public Uni<Response> createNewEmployee(Employee employee){
        LOG.info("POST http://localhost:8080/employee : processing to add new employee.");
        return employeeService.addNewEmployee(employee)
                .onItem().transform(employee1 -> Response.status(Response.Status.CREATED).entity(employee1).build())
                .onFailure().recoverWithItem(fail ->Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(fail.getMessage()).build());
    }

    @GET
    public Uni<Response> getAllEmployees(){
        LOG.info("GET http://localhost:8080/employee : Listing all employees.");
        return employeeService.getAllEmployees()
                .onItem().transform(response -> Response.status(Response.Status.OK).entity(response).build())
                .onFailure().recoverWithItem(failure-> Response.status(Response.Status.NO_CONTENT).entity("The record is empty. \nAdd employees.").build());
    }

    @PUT
    @Path("/{id}")
    public Uni<Response> updateEmployee(@PathParam("id") String id, Employee employee){
        LOG.info("PUT http://localhost:8080/employee/{id} : updating employee data.");
        return employeeService.updateEmployee(id, employee)
                .onItem().transform(response -> Response.status(Response.Status.OK).entity(response).build())
                .onFailure().recoverWithItem(failure-> Response.status(Response.Status.NOT_FOUND).entity(failure.getMessage()).build());
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> deleteEmployee(@PathParam("id") String id){
        LOG.info("DELETE http://localhost:8080/employee/{id} : delete employee with given id.");
        return employeeService.deleteEmployee(id)
                .onItem().transform(deleted -> {
                    if (deleted) {
                        return Response.ok().entity("Employee deleted successfully.").build();
                    } else {
                        return Response.status(Response.Status.NOT_FOUND).entity(id + " id is not found").build();
                    }
                });
    }
}
