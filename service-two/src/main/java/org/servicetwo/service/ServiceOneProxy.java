package org.servicetwo.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.servicetwo.entity.BookEntity;


@Path("/books")
@ApplicationScoped
@RegisterRestClient(configKey = "serviceone")
public interface ServiceOneProxy {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    Uni<Response> giveDelay();

    @POST
    @Path("/book")
    Uni<Response> acceptABook(BookEntity bookEntity);


    @POST
    @Path("/error/{input}")
    Uni<Response> throwAnException(@PathParam("input") int num);
}
