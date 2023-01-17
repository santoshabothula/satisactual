package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentTypeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
    @JsonProperty("cod_assignment_typ")
    private String codAssignmentType;

    @NotBlank
    @Size(min = 1, max = 24)
    @JsonProperty("txt_assignment_type_desc")
    private String assignmentTypeDesc;

    @JsonProperty("flg_default_value")
    private FlagYesNo isDefaultValue;
}
