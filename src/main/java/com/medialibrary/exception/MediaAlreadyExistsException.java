package com.medialibrary.exception;

public class MediaAlreadyExistsException extends RuntimeException {

  public MediaAlreadyExistsException(String message) {
    super(message);
  }
}
