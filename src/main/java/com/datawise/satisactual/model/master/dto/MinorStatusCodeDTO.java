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
public class MinorStatusCodeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_status")
    private String codStatus;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_status_desc")
    private String statusDesc;

    @Size(min = 1, max = 1)
	@JsonProperty("enu_entity_type")
    private String entityType;

	@JsonProperty("flg_apply_to_individual")
    private FlagYesNo applyToIndividual;

	@JsonProperty("flg_apply_to_organisation")
    private FlagYesNo applyToOrganisation;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_txn")
    private String txn;

}
