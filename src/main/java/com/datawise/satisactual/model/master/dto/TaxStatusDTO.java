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
public class TaxStatusDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_tax_status")
    private String codTaxStatus;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_tax_status_desc")
    private String taxStatusDesc;

	@JsonProperty("flg_apply_to_individual")
    private FlagYesNo applyToIndividual;

	@JsonProperty("flg_apply_to_organisation")
    private FlagYesNo applyToOrganisation;
}
