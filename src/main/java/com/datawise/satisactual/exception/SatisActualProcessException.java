package com.datawise.satisactual.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SatisActualProcessException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private String message;
}
