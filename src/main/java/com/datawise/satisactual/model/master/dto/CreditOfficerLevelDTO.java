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
public class CreditOfficerLevelDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_credit_officer_level")
    private String codCreditOfficerLevel;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_credit_officer_level_desc")
    private String creditOfficerLevelDesc;

	@JsonProperty("amt_max_approving_authority")
    private Double maxApprovingAuthority;

	@JsonProperty("amt_single_approver_limit")
    private Double singleApproverLimit;

	@JsonProperty("amt_annual_approval_limit")
    private Double annualApprovalLimit;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;
}
