package ru.sabah.struktura.handlers.errors;

import org.glassfish.jersey.message.internal.OutboundJaxrsResponse;
import org.glassfish.jersey.message.internal.OutboundMessageContext;
import ru.sabah.struktura.services.ServiceError;

import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

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
