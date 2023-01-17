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
public class CovenantTypeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_covenant_type")
    private String codCovenantType;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_covenant_type_desc")
    private String covenantTypeDesc;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;
}
