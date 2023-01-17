package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
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
public class ContactOutcomeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_contact_outcome")
    private String codContactOutcome;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_contact_outcome_desc")
    private String contactOutcomeDesc;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_parent_outcome")
    private String parentOutcome;

    @Size(min = 1, max = 4)
	@JsonProperty("enu_base_channel_type")
    private String baseChannelType;

	@JsonProperty("flg_valid_outcome")
    private FlagYesNo validOutcome;

	@JsonProperty("flg_outbound")
    private FlagYesNo outbound;
}
