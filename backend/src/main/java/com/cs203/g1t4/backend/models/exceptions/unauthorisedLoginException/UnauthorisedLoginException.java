package com.cs203.g1t4.backend.models.exceptions.unauthorisedLoginException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED) // 401 Error
public class UnauthorisedLoginException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  
  public UnauthorisedLoginException(String message) {
    super(message);
  }
  
}
