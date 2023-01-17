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
public class InsClaimTypeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_claim_typ")
    private String codClaimTyp;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_claim_typ_desc")
    private String claimTypDesc;

    @Size(min = 1, max = 1)
	@JsonProperty("enu_loss_type")
    private String lossType;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;
}
