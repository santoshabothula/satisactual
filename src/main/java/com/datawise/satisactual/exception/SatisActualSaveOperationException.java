package com.datawise.satisactual.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SatisActualSaveOperationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String message;
    private List<?> existingRecords;
    private final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
}
