package com.example.employee.assignment.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEmployeeNotFound(EmployeeNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        StackTraceElement element = ex.getStackTrace()[0];

        error.put("timestamp", LocalDateTime.now());
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("error", "Not Found");
        error.put("message", ex.getMessage()); // this will now say "Employee with ID 5 is not found"
        error.put("path", "/api/employees/" + element.getLineNumber()); // optionally customize further
        error.put("class", element.getClassName());
        error.put("method", element.getMethodName());
        error.put("line", element.getLineNumber());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex, HttpServletRequest request) {
        LOGGER.log(Level.SEVERE, "Unhandled Exception: " + ex.getMessage(), ex);

        StackTraceElement element = ex.getStackTrace()[0];

        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        error.put("message", ex.getMessage());
        error.put("path", request.getRequestURI());
        error.put("class", element.getClassName());
        error.put("method", element.getMethodName());
        error.put("line", element.getLineNumber());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
