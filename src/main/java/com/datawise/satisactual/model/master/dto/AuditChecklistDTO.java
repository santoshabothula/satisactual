package com.datawise.satisactual.model.master.dto;

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
public class AuditChecklistDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
    @JsonProperty("cod_audit_item")
    private String codAuditItem;

    @NotBlank
    @Size(min = 1, max = 24)
    @JsonProperty("txt_item_desc")
    private String itemDesc;

    @JsonProperty("num_display_sequence")
    private Integer displaySequence;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("flg_group_loan_check")
    private String groupLoanCheck;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("flg_commercial_loan_check")
    private String commercialLoanCheck;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("flg_secured_loan_check")
    private String securedLoanCheck;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("flg_guarantor_required_check")
    private String guarantorRequiredCheck;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("flg_fixed_rate_check")
    private String fixedRateCheck;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("flg_multi_disburse_check")
    private String multiDisburseCheck;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("flg_debt_consolidation_check")
    private String debtConsolidationCheck;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("flg_rate_discount_check")
    private String rateDiscountCheck;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("flg_early_payoff_allowed_check")
    private String earlyPayoffAllowedCheck;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("flg_interest_subvention_check")
    private String interestSubventionCheck;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("flg_priority_sector_check")
    private String prioritySectorCheck;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("flg_default_value")
    private String defaultValue;
}
