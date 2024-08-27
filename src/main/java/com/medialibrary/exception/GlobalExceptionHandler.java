package com.medialibrary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = MediaAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public @ResponseBody ErrorResponse handleMediaAlreadyExistsException(
      MediaAlreadyExistsException e) {
    return new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage());
  }

  @ExceptionHandler(value = NoSuchMediaExistsException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public @ResponseBody ErrorResponse handleNoSuchMediaExistsException(
      NoSuchMediaExistsException e) {
    return new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
  }
}
