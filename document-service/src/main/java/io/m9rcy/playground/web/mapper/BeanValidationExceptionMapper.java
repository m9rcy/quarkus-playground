package io.m9rcy.playground.web.mapper;


import io.m9rcy.playground.web.model.response.ErrorResponse;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BeanValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {

        ErrorResponse errorResponse = new ErrorResponse();

        e.getConstraintViolations().iterator().forEachRemaining(constraint -> {
            errorResponse.getBody().add(constraint.getMessage());
        });

        return Response.ok(errorResponse).status(Response.Status.BAD_REQUEST).build();
    }

}
