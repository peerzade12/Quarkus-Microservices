package org.servicetwo.mapper;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.servicetwo.entity.ErrorMessage;
import org.servicetwo.exception.CustomArithmeticException;

@Provider
public class CustomArithmeticExceptionMapper implements ExceptionMapper<CustomArithmeticException> {
    @Override
    public Response toResponse(CustomArithmeticException exception) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(exception.getStatusCode());
        errorMessage.setMessage(exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).type(MediaType.APPLICATION_JSON).build();
    }
}
