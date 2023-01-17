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
public class EthnicCodeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_ethnicity")
    private String codEthnicity;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_ethnicity_desc")
    private String ethnicityDesc;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;

}
