package com.remitly.exception;

public class SwiftCodeAlreadyExistsException extends RuntimeException {
    public SwiftCodeAlreadyExistsException(String message) {
        super(message);
    }
}