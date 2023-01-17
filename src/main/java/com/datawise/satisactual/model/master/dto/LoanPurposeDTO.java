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
public class LoanPurposeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_loan_purpose")
    private String codLoanPurpose;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_loan_purpose_desc")
    private String loanPurposeDesc;

    @Size(min = 1, max = 1)
	@JsonProperty("cod_linked_coll_type")
    private String linkedCollType;

	@JsonProperty("flg_education")
    private FlagYesNo education;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;

	@JsonProperty("flg_psl_qualified")
    private FlagYesNo pslQualified;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_priority_sector_category")
    private String prioritySectorCategory;
}
