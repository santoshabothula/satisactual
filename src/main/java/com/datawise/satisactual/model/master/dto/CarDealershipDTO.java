package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarDealershipDTO extends BaseDTO {

    @NotNull
	@JsonProperty("id_third_party_dealer")
    private Long idThirdPartyDealer;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_make_specialization")
    private String makeSpecialization;

	@JsonProperty("flg_lon_appl_originator")
    private FlagYesNo lonApplOriginator;

    @Size(min = 1, max = 6)
	@JsonProperty("cod_servicing_branch")
    private String servicingBranch;
}
