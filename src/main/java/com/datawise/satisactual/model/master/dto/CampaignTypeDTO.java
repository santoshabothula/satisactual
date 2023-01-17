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
public class CampaignTypeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_campaign_type")
    private String codCampaignType;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_campaign_type_desc")
    private String campaignTypeDesc;

	@JsonProperty("flg_sales_campaign")
    private FlagYesNo salesCampaign;

	@JsonProperty("flg_employee_survey")
    private FlagYesNo employeeSurvey;

	@JsonProperty("num_min_size_for_reporting")
    private Integer minSizeForReporting;

	@JsonProperty("flg_dnc_scrub_reqd")
    private FlagYesNo dncScrubReqd;

	@JsonProperty("flg_structured")
    private FlagYesNo structured;

	@JsonProperty("flg_response_reqd")
    private FlagYesNo responseReqd;

	@JsonProperty("flg_targetlist_mandatory")
    private FlagYesNo targetListMandatory;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;
}
