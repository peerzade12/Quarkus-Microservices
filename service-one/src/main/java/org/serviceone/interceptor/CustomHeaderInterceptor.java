package org.serviceone.interceptor;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.WriterInterceptor;
import jakarta.ws.rs.ext.WriterInterceptorContext;

import java.io.IOException;
import java.time.LocalDate;

@Provider
public class CustomHeaderInterceptor implements WriterInterceptor {
    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        context.getHeaders().add("Framework", "Quarkus");
        context.getHeaders().add("Date", LocalDate.now());
        context.proceed();
    }
}
