package com.cs203.g1t4.backend.models.exceptions.notAvailableException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // 404 Error
public class NotAvailableException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  
  public NotAvailableException(String message) {
    super(message);
  }
  
}
