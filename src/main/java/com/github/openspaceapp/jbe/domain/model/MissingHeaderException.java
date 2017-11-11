package com.github.openspaceapp.jbe.domain.model;

public class MissingHeaderException extends RuntimeException {
    public MissingHeaderException(String message) {
        super(message);
    }
}
