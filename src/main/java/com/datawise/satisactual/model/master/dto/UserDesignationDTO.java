package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDesignationDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_designation")
    private String codDesignation;

    @NotNull
	@JsonProperty("id_third_party")
    private Long idThirdParty;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_designation_desc")
    private String designationDesc;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_parent_designation")
    private String parentDesignation;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;
}
