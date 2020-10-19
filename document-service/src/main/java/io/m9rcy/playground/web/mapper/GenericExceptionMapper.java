package io.m9rcy.playground.web.mapper;

import javax.ws.rs.core.Response;

//@Provider
public class GenericExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<RuntimeException> {
    @Override
    public Response toResponse(RuntimeException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
