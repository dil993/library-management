package com.library.config;

import com.library.exception.AlreadyCheckedOutException;
import com.library.exception.BookNotFoundException;
import com.library.exception.InvalidReturnException;
import com.library.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class LibraryControllerAdvice {
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> handleBookNotFound(BookNotFoundException ex, WebRequest request) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(BookNotFoundException ex, WebRequest request) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(AlreadyCheckedOutException.class)
    public ResponseEntity<Object> handleAlreadyCheckedOut(AlreadyCheckedOutException ex, WebRequest request) {
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(InvalidReturnException.class)
    public ResponseEntity<Object> handleInvalidReturn(InvalidReturnException ex, WebRequest request) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex, WebRequest request) {
        return buildResponse("Unexpected error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<Object> buildResponse(String message, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(body, status);
    }
}
