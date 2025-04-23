package com.remitly.exception;

public class SwiftCodeNotFoundException extends RuntimeException {
    public SwiftCodeNotFoundException(String message) {
        super("SWIFT code not found: "+ message);
    }
}