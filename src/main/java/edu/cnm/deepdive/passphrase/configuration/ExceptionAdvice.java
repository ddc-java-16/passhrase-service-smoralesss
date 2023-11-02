package edu.cnm.deepdive.passphrase.configuration;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND,
      reason = "Resource (user or passphrase) doesn't exist.")
  public void notFound() {
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST,
      reason = "Request parameters or payload are invalid.")
  public void badRequest() {
  }

  @ExceptionHandler(SQLException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST,
      reason = "Request content violates SQL constraints; not written to database.")
  public void sqlConstraintViolation() {
  }
}
