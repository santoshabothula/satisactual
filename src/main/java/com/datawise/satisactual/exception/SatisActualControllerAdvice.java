package com.datawise.satisactual.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.NoSuchAlgorithmException;

@ControllerAdvice
public class SatisActualControllerAdvice {

    @ExceptionHandler(value = SatisActualProcessException.class)
    public ResponseEntity<Object> exception(SatisActualProcessException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<Object> exception(BadCredentialsException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> exception(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = NoSuchAlgorithmException.class)
    public ResponseEntity<Object> exception(NoSuchAlgorithmException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
