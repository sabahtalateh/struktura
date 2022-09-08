package ru.sabah.struktura.handlers.errors;

import org.glassfish.jersey.message.internal.OutboundJaxrsResponse;
import org.glassfish.jersey.message.internal.OutboundMessageContext;
import ru.sabah.struktura.services.ServiceError;

import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ServiceErrorMapper implements ExceptionMapper<ServiceError> {
    @Override
    public Response toResponse(ServiceError exception) {
        var ctx = new OutboundMessageContext((Configuration) null);
        var ent = ru.sabah.struktura.handlers.Response.fromException(exception);
        ctx.setEntity(ent);

        return new OutboundJaxrsResponse(Response.Status.fromStatusCode(ent.getCode()), ctx);
    }
}
