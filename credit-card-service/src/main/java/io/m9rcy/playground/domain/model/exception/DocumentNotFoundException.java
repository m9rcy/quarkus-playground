package io.m9rcy.playground.domain.model.exception;

public class DocumentNotFoundException extends BusinessException {
    public DocumentNotFoundException() {
        super("Document not found");
    }
}
