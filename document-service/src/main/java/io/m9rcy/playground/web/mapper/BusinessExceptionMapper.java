package io.m9rcy.playground.web.mapper;

import io.m9rcy.playground.domain.model.exception.BusinessException;
import io.m9rcy.playground.web.exception.ResourceNotFoundException;
import io.m9rcy.playground.web.exception.UnauthorizedException;
import io.m9rcy.playground.web.model.response.ErrorResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

  private Map<Class<? extends BusinessException>, BusinessExceptionHandler> exceptionMapper;

  public BusinessExceptionMapper() {
    this.exceptionMapper = configureExceptionMapper();
  }

  private Map<Class<? extends BusinessException>, BusinessExceptionHandler>
      configureExceptionMapper() {

    Map<Class<? extends BusinessException>, BusinessExceptionHandler> handlerMap = new HashMap<>();

    handlerMap.put(ResourceNotFoundException.class, notFound());
    handlerMap.put(UnauthorizedException.class, unauthorized());

    return handlerMap;
  }

  private BusinessExceptionHandler notFound() {
    return exceptionHandler(
        Response.Status.NOT_FOUND.name(), Response.Status.NOT_FOUND.getStatusCode());
  }

  private BusinessExceptionHandler conflict() {
    return exceptionHandler(
        Response.Status.CONFLICT.name(), Response.Status.CONFLICT.getStatusCode());
  }

  private BusinessExceptionHandler unauthorized() {
    return exceptionHandler(
        Response.Status.UNAUTHORIZED.name(), Response.Status.UNAUTHORIZED.getStatusCode());
  }

  private BusinessExceptionHandler exceptionHandler(String message, int httpStatusCode) {
    return ex -> {
      String resultMessage = message;
      if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
        resultMessage = ex.getMessage();
      }
      return errorResponse(resultMessage, httpStatusCode);
    };
  }

  private Response errorResponse(String errorMessage, int httpStatusCode) {
    return Response.ok(new ErrorResponse(errorMessage)).status(httpStatusCode).build();
  }

  @Override
  public Response toResponse(BusinessException businessException) {
    return this.exceptionMapper.get(businessException.getClass()).handler(businessException);
  }

  private interface BusinessExceptionHandler {
    Response handler(BusinessException ex);
  }
}
