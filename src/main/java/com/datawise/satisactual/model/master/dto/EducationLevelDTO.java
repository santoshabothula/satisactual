package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EducationLevelDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_education_level")
    private String codEducationLevel;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_education_level_desc")
    private String educationLevelDesc;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;
}
