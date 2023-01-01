package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AssignmentTypeDTO extends MakerCheckerDTO {

    @NotBlank
    @Size(min = 1, max = 4)
    @JsonProperty("cod_assignment_typ")
    private String codAssignmentType;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("cod_rec_status")
    private String codRecordStatus;

    @NotBlank
    @JsonProperty("txt_assignment_type_desc")
    private String assignmentTypeDesc;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("flg_default_value")
    private String isDefaultValue;
}
