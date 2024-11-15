package com.medialibrary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = UserNotFoundException.class)
  public @ResponseBody ErrorResponse handleUserNotFoundException(UserNotFoundException e) {
    return new ErrorResponse(
        HttpStatus.NOT_FOUND.value(),
        HttpStatus.NOT_FOUND.getReasonPhrase(),
        e.getMessage());
  }

  @ExceptionHandler(value = UsernameNotFoundException.class)
  public @ResponseBody ErrorResponse handleUsernameNotFoundException(
      UsernameNotFoundException e) {
    return new ErrorResponse(
        HttpStatus.NOT_FOUND.value(),
        HttpStatus.NOT_FOUND.getReasonPhrase(),
        e.getMessage());
  }

  @ExceptionHandler(value = UserAlreadyExistsException.class)
  public @ResponseBody ErrorResponse handleUserAlreadyExistsException(
      UserAlreadyExistsException e) {
    return new ErrorResponse(
        HttpStatus.CONFLICT.value(),
        HttpStatus.CONFLICT.getReasonPhrase(),
        e.getMessage());
  }

  @ExceptionHandler(value = MediaAlreadyExistsException.class)
  public @ResponseBody ErrorResponse handleMediaAlreadyExistsException(
      MediaAlreadyExistsException e) {
    return new ErrorResponse(
        HttpStatus.CONFLICT.value(),
        HttpStatus.CONFLICT.getReasonPhrase(),
        e.getMessage());
  }

  @ExceptionHandler(value = NoSuchMediaExistsException.class)
  public @ResponseBody ErrorResponse handleNoSuchMediaExistsException(
      NoSuchMediaExistsException e) {
    return new ErrorResponse(
        HttpStatus.NOT_FOUND.value(),
        HttpStatus.NOT_FOUND.getReasonPhrase(),
        e.getMessage());
  }
}
