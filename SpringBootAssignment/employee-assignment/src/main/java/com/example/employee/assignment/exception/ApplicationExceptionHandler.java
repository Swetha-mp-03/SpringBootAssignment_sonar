package com.example.employee.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(ApplicationExceptionHandler.class.getName());

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeNotFound(EmployeeNotFoundException ex) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            RuntimeException.class,
            NullPointerException.class,
            IllegalStateException.class
    })
    public ResponseEntity<ErrorResponse> handleCommonRuntimeExceptions(Exception ex) {
        LOGGER.log(Level.SEVERE, "Unhandled exception: {0}", new Object[]{ex.getMessage(), ex});
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, HttpStatus status) {
        ErrorResponse error = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, status);
    }
}
