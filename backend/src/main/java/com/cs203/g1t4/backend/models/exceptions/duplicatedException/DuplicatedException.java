package com.cs203.g1t4.backend.models.exceptions.duplicatedException;

public class DuplicatedException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  
  public DuplicatedException(String message) {
    super(message);
  }
  
}
