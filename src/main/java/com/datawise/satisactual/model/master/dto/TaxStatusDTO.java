package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaxStatusDTO extends BaseDTO {

	@JsonProperty("cod_tax_status")
    private String codTaxStatus;

	@JsonProperty("txt_tax_status_desc")
    private String taxStatusDesc;

	@JsonProperty("flg_apply_to_individual")
    private String applyToIndividual;

	@JsonProperty("flg_apply_to_organisation")
    private String applyToOrganisation;
}
