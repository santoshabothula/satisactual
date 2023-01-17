package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FundingLineDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_funding_line")
    private String codFundingLine;

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_funder")
    private String codFunder;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_funding_line_desc")
    private String fundingLineDesc;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_currency")
    private String currency;

	@JsonProperty("amt_funding_line")
    private Double fundingLine;

	@JsonProperty("amt_funding_line_fcy")
    private Double fundingLineFcy;

	@JsonProperty("dat_sanction")
    private LocalDate sanction;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_instructions")
    private String instructions;

	@JsonProperty("flg_reserved")
    private FlagYesNo reserved;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;
}
