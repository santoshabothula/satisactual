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
public class EmployerCategoryDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_employer_category")
    private String codEmployerCategory;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_employer_category_desc")
    private String employerCategoryDesc;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;

}
