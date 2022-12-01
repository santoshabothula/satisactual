package com.datawise.satisactual.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SatisActualProcessException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String message;
}
