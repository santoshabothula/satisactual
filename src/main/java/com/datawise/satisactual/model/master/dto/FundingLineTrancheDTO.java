package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FundingLineTrancheDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_funding_line_tranche")
    private String codFundingLineTranche;

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_funding_line")
    private String codFundingLine;

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_funder")
    private String codFunder;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_tranche_desc")
    private String trancheDesc;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_assignment_typ")
    private String assignmentTyp;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_currency")
    private String currency;

	@JsonProperty("amt_tranche")
    private Double tranche;

	@JsonProperty("amt_funding_line_fcy")
    private Double fundingLineFcy;

	@JsonProperty("dat_disbursed")
    private LocalDate disbursed;

	@JsonProperty("dat_first_payment")
    private LocalDate firstPayment;

	@JsonProperty("dat_last_payment")
    private LocalDate lastPayment;

	@JsonProperty("rat_interest")
    private Double ratInterest;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;

    @Size(min = 1, max = 24)
	@JsonProperty("cod_ifsc_bank_branch")
    private String ifscBankBranch;

    @Size(min = 1, max = 16)
	@JsonProperty("num_nostro_account")
    private String nostroAccount;
}
