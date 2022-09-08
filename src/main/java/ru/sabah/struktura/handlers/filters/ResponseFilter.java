package ru.sabah.struktura.handlers.filters;

import ru.sabah.struktura.handlers.Response;

import javax.ws.rs.container.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.annotation.Annotation;

import static java.net.HttpURLConnection.HTTP_OK;

@Provider
@PreMatching
public class ResponseFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        var entity = responseContext.getEntity();
        if (entity instanceof Response<?>) {
            responseContext.setStatus(((Response<?>) entity).getCode());
            return;
        }

        if (entity == null) {
            entity = new Response<>(responseContext.getStatus());
            responseContext.setEntity(entity, new Annotation[0], MediaType.APPLICATION_JSON_TYPE);
        }
    }
}
