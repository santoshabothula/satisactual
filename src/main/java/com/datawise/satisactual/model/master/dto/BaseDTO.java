package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.CodRecordStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseDTO extends MakerCheckerDTO {
    @JsonProperty("cod_rec_status")
    private CodRecordStatus codRecordStatus;
}
