package com.cs203.g1t4.backend.models.exceptions.invalidIdException;

public class InvalidIdException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  
  public InvalidIdException(String message) {
    super(message);
  }
}
