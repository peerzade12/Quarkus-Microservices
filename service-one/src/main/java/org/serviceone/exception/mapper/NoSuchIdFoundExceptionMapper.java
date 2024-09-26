package org.serviceone.exception.mapper;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.serviceone.exception.object.ErrorMessage;
import org.serviceone.exception.NoSuchIdFoundException;

@Provider
public class NoSuchIdFoundExceptionMapper implements ExceptionMapper<NoSuchIdFoundException> {
    @Override
    public Response toResponse(NoSuchIdFoundException exception) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(exception.getStatusCode());
        errorMessage.setMessage(exception.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(errorMessage).type(MediaType.APPLICATION_JSON).build();
    }
}

