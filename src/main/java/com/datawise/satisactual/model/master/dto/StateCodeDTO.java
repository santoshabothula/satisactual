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
public class StateCodeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 12)
	@JsonProperty("cod_state")
    private String codState;

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_country")
    private String codCountry;

    @Size(min = 1, max = 4)
	@JsonProperty("txt_state_short_code")
    private String stateShortCode;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_state_name")
    private String stateName;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;
}
