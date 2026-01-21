package com.example.shared.errors;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
    super(message);
  }
}