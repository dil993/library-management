package com.library.exception;

public class InvalidReturnException extends RuntimeException {
    public InvalidReturnException(String message) {
        super(message);
    }
}