package com.medialibrary.exception;

public class NoSuchMediaExistsException extends RuntimeException {

  public NoSuchMediaExistsException(String message) {
    super(message);
  }
}
