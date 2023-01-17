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
public class IndustryGrpDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_industry_grp")
    private String codIndustryGrp;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_industry_name")
    private String industryName;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_parent_industry_grp")
    private String parentIndustryGrp;

    @Size(min = 1, max = 8)
	@JsonProperty("txt_industry_sic_code")
    private String industrySicCode;

    @Size(min = 1, max = 8)
	@JsonProperty("txt_industry_naics_code")
    private String industryNaicsCode;

	@JsonProperty("flg_psl_qualified")
    private FlagYesNo pslQualified;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_priority_sector_category")
    private String prioritySectorCategory;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;

	@JsonProperty("amt_exposure_limit")
    private Double exposureLimit;

	@JsonProperty("amt_annual_sanctions_limit")
    private Double annualSanctionsLimit;

}
