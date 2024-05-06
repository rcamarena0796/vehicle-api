package com.unow.vehicle.exception;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * CustomGlobalExceptionHandler.
 */
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

  // error handle for @Valid
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", new Date());
    body.put("status", status.value());

    //Get all errors
    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(x -> x.getDefaultMessage())
        .collect(Collectors.toList());

    body.put("errors", errors);

    return new ResponseEntity<>(body, headers, status);

  }

  @ExceptionHandler(PropertyReferenceException.class)
  public ResponseEntity<Object> handleDatabaseException(PropertyReferenceException ex, WebRequest request) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", "Sort failed!");
    body.put("error", ex.getLocalizedMessage());
    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
  public ResponseEntity<Object> handleDatabaseException(SQLIntegrityConstraintViolationException ex, WebRequest request) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", "Insert failed, license plate field must be unique and not null");
    body.put("error", ex.getLocalizedMessage());
    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
