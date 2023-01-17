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
public class PrioritySectorCategoryDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_priority_sector_category")
    private String codPrioritySectorCategory;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_priority_sector_desc")
    private String prioritySectorDesc;

	@JsonProperty("max_loan_amt_to_qualify")
    private Double loanAmtToQualify;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_document")
    private String document;
}
