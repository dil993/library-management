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
    /**
     * Handles BookNotFoundException by returning a 404 Not Found response.
     * This exception occurs when a requested book is not found in the system.
     *
     * @param ex the caught BookNotFoundException
     * @param request the web request that resulted in the exception
     * @return ResponseEntity containing error details with HTTP 404 status
     */
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> handleBookNotFound(BookNotFoundException ex, WebRequest request) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * Handles UserNotFoundException by returning a 404 Not Found response.
     * This exception occurs when a requested user is not found in the system.
     *
     * @param ex the caught UserNotFoundException
     * @param request the web request that resulted in the exception
     * @return ResponseEntity containing error details with HTTP 404 status
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex, WebRequest request) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * Handles AlreadyCheckedOutException by returning a 409 Conflict response.
     * This exception occurs when attempting to check out a book that is already checked out.
     *
     * @param ex the caught AlreadyCheckedOutException
     * @param request the web request that resulted in the exception
     * @return ResponseEntity containing error details with HTTP 409 status
     */
    @ExceptionHandler(AlreadyCheckedOutException.class)
    public ResponseEntity<Object> handleAlreadyCheckedOut(AlreadyCheckedOutException ex, WebRequest request) {
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT, request);
    }

    /**
     * Handles InvalidReturnException by returning a 400 Bad Request response.
     * This exception occurs when a user attempts to return a book they didn't check out.
     *
     * @param ex the caught InvalidReturnException
     * @param request the web request that resulted in the exception
     * @return ResponseEntity containing error details with HTTP 400 status
     */
    @ExceptionHandler(InvalidReturnException.class)
    public ResponseEntity<Object> handleInvalidReturn(InvalidReturnException ex, WebRequest request) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Global exception handler that catches all unhandled exceptions.
     * Returns a 500 Internal Server Error response for unexpected errors.
     *
     * @param ex the caught Exception
     * @param request the web request that resulted in the exception
     * @return ResponseEntity containing error details with HTTP 500 status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex, WebRequest request) {
        return buildResponse("Unexpected error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * Builds a standardized error response entity with common error details.
     *
     * @param message the error message to include in the response
     * @param status the HTTP status code for the response
     * @param request the web request that resulted in the error
     * @return ResponseEntity containing the error details and appropriate status
     */
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
