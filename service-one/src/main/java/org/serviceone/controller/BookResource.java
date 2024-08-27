package org.serviceone.controller;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.serviceone.entity.Book;
import org.serviceone.service.BookService;

/**
 * service-one implements three endpoints and interceptor adding a custom header.
 * Architecture : REST[Controller->service->serviceImpl]
 */

@Path("/books")
public class BookResource {

    @Inject
    BookService bookService;

    public static final Logger LOG = Logger.getLogger(BookResource.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<Response> giveDelay(){
        LOG.info("GET http://localhost:8080/books : Response will come after some delay.");
        return bookService.giveDelayOf();
    }

    @POST
    @Path("/book")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> acceptABook(Book book){
        LOG.info("POST http://localhost:8080/books/book : Response will have 'Success'.");
        return bookService.acceptABook(book);
    }

    @POST
    @Path("/error/{input}")
    public Uni<Response> throwAnException(@PathParam("input") int num){
        LOG.info("POST http://localhost:8080/books/error/{input} : This method throws an exception.");
        return bookService.throwAnException(num);
    }
}
