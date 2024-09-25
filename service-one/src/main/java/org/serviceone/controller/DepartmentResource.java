package org.serviceone.controller;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.serviceone.entity.Department;
import org.serviceone.service.DepartmentService;

@Path("/department")
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentResource {

    @Inject
    DepartmentService departmentService;

    public static final Logger LOG = Logger.getLogger(BookResource.class);

    @POST
    public Uni<Response> createNewDepartment(Department department){
        LOG.info("POST http://localhost:8080/department : Add New Department.");
        return departmentService.addNewDepartment(department)
                .onItem().transform(s->Response.status(Response.Status.CREATED).entity(department).build())
                .onFailure().recoverWithItem(Response.status(Response.Status.BAD_REQUEST).build());
    }

    @GET
    public Uni<Response> getAllDepartment(){
        LOG.info("GET http://localhost:8080/department :  Department List.");
        return departmentService.getAllDepartment()
                .onItem().transform(dep->Response.status(Response.Status.OK).entity(dep).build())
                .onFailure().recoverWithItem(Response.status(Response.Status.BAD_GATEWAY).build());
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> deleteDepartmentById(@PathParam("id") String id){
        LOG.info("DELETE http://localhost:8080/department/{id} :  Department delete.");
        return departmentService.deleteDepartment(id).onItem().transform(deleted->{
            if (deleted) return Response.status(Response.Status.OK).entity("Department with " + id + " id successfully deleted").build();
            else return Response.status(Response.Status.NOT_FOUND).entity(id + " id is not found in records").build();
        });
    }

}
