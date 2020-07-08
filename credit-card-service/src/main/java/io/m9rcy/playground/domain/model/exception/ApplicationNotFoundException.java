package io.m9rcy.playground.domain.model.exception;

public class ApplicationNotFoundException extends BusinessException{
    public ApplicationNotFoundException() {
        super("Application not found");
    }
}
