package com.basedocker.domain.exception;

public class TemplateNotAvailableException extends RuntimeException {
    public TemplateNotAvailableException(Exception s) {
        super(s);
    }
}
