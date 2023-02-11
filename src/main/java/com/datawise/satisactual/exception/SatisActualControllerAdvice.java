package com.datawise.satisactual.exception;

import com.datawise.satisactual.model.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
public class SatisActualControllerAdvice {

    @ExceptionHandler(value = SatisActualProcessException.class)
    public ResponseEntity<Object> exception(SatisActualProcessException exception) {
        return new ResponseEntity<>(
                CustomResponse.builder().status(exception.getStatus().value()).message(exception.getMessage()).build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = SatisActualSaveOperationException.class)
    public CustomResponse exception(SatisActualSaveOperationException exception) {
        return CustomResponse.builder().
                status(HttpStatus.BAD_REQUEST.value()).
                message(exception.getMessage()).
                data(exception.getExistingRecords()).
                build();
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<Object> exception(BadCredentialsException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(value = NoSuchElementException.class)
    public CustomResponse exception(NoSuchElementException exception) {
        return CustomResponse.builder().
                status(HttpStatus.NOT_FOUND.value()).
                message(exception.getMessage()).
                build();
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> exception(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = NoSuchAlgorithmException.class)
    public ResponseEntity<Object> exception(NoSuchAlgorithmException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public List<CustomResponse> exception(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    private List<CustomResponse> processFieldErrors(List<org.springframework.validation.FieldError> fieldErrors) {
        List<CustomResponse> errors = new ArrayList<>();
        for (org.springframework.validation.FieldError fieldError: fieldErrors) {
            errors.add(
                    CustomResponse.builder().
                            status(HttpStatus.BAD_REQUEST.value()).
                            message(fieldError.getField() + ": " + fieldError.getDefaultMessage()).build()
            );
        }
        return errors;
    }
}
