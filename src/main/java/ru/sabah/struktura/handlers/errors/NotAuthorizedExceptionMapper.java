package ru.sabah.struktura.handlers.errors;

import org.glassfish.jersey.message.internal.OutboundJaxrsResponse;
import org.glassfish.jersey.message.internal.OutboundMessageContext;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;

@Provider
public class NotAuthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {
    @Override
    public Response toResponse(NotAuthorizedException exception) {
        var ctx = new OutboundMessageContext((Configuration) null);
        var ent = ru.sabah.struktura.handlers.Response.unauthorized().addError("unauthorized");
        ctx.setEntity(ent, new Annotation[0], MediaType.APPLICATION_JSON_TYPE);

        return new OutboundJaxrsResponse(Response.Status.fromStatusCode(ent.getCode()), ctx);
    }
}
