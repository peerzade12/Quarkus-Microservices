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
import org.servicetwo.entity.Book;
import org.servicetwo.exception.CustomArithmeticException;
import org.servicetwo.service.ServiceOneProxy;

import java.time.temporal.ChronoUnit;

/**
 * Client service-two provides endpoints to call service-one endpoints.
 * It implements fault tolerance, exception handling, filter to log info for request-response.
 */

@Path("/books")
public class ServiceOneResource {

    @Inject
    @RestClient
    ServiceOneProxy serviceOneProxy;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Retry(maxRetries = 2)
    @Timeout(value = 15, unit = ChronoUnit.SECONDS)
    @Fallback(fallbackMethod = "handleConnectionRefusedFallback")
    public Uni<Response> resultAfterDelay() {
        return serviceOneProxy.giveDelay();
    }

    @POST
    @Path("/book")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Fallback(fallbackMethod = "handleTimeOutFallback")
    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.6, delay = 20, delayUnit = ChronoUnit.SECONDS)
    public Uni<Response> acceptABook(Book bookEntity) {
        return serviceOneProxy.acceptABook(bookEntity);
    }

    @POST
    @Path("/error/{input}")
    @Retry(maxRetries = 1)
    public Uni<Response> throwAnException(@PathParam("input") int num) {
        return serviceOneProxy.throwAnException(num)
                .onFailure()
                .transform(error-> new CustomArithmeticException(Response.Status.BAD_REQUEST.getStatusCode(), "Cannot divide by zero"));
    }

    public Uni<Response> handleConnectionRefusedFallback() {
        return Uni.createFrom()
                .item(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity("Connection Refused : server down.")
                        .build()
                );
    }

    public Uni<Response> handleTimeOutFallback(Book bookEntity) {
        return Uni.createFrom()
                .item(Response.status(Response.Status.REQUEST_TIMEOUT)
                        .entity("Timeout : It took too long.")
                        .build());
    }

}
