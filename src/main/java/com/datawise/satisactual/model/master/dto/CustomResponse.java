package com.datawise.satisactual.model.master.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CustomResponse {
    private String message;
    private Integer status;
}
