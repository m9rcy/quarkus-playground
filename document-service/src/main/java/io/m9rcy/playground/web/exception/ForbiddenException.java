package io.m9rcy.playground.web.exception;


import io.m9rcy.playground.domain.model.exception.BusinessException;

public class ForbiddenException extends BusinessException {
    public ForbiddenException() {
        super("Forbidden");
    }
}
