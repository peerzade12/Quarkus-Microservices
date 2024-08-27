package org.servicetwo.filters;

import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.client.ClientResponseContext;
import jakarta.ws.rs.client.ClientResponseFilter;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.io.IOException;
@Provider
public class LoggerFilter implements ClientRequestFilter, ClientResponseFilter {

    private final Logger LOG = Logger.getLogger(LoggerFilter.class);

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        LOG.info("Request : " + requestContext.getMethod() + " " + requestContext.getUri());
    }

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        LOG.info("Response : " + requestContext.getMethod() + " " + requestContext.getUri() +  " | Status Code : " + responseContext.getStatus()) ;
    }
}
