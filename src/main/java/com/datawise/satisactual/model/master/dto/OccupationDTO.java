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
public class OccupationDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_occupation")
    private String codOccupation;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_occupation_desc")
    private String occupationDesc;

    @Size(min = 1, max = 1)
	@JsonProperty("enu_occupation_type")
    private String occupationType;

	@JsonProperty("flg_psl_qualified")
    private FlagYesNo pslQualified;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_priority_sector_category")
    private String prioritySectorCategory;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_primary_income_src")
    private String primaryIncomeSrc;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;
}
