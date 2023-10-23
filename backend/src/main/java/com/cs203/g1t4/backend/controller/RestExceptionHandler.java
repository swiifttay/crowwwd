package com.cs203.g1t4.backend.controller;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cs203.g1t4.backend.models.exceptions.DuplicatedException;

import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object>
        handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                            HttpHeaders headers,
                                            HttpStatusCode status,
                                            WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        body.put("message", errors.get(0));
        body.put("path", request.getDescription(false));
        return new ResponseEntity<>(body, headers, status);
    }

    // @ExceptionHandler(RuntimeException.class)
    // protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex,
    //                                         HttpStatusCode status) {
    //     Map<String, Object> body = new LinkedHashMap<>();
    //     body.put("timestamp", new Date());
    //     body.put("status", status.value());
    //     body.put("error", status.getClass());
    //     body.put("message", ex.getMessage());
    //     return new ResponseEntity<>(body, status);
    // }

    @ExceptionHandler(DuplicatedException.class)
    protected ResponseEntity<Object> handleDuplicatedException(DuplicatedException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public void handleTypeMismatch(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    // private method to get the errors for invalid fields
    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new TreeMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
