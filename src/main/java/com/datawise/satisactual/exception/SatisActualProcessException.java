package com.datawise.satisactual.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SatisActualProcessException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String message;
    private final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
}
