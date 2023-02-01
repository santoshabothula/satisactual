package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse {
    private String message;
    private Integer status;
    private Object data;
}
