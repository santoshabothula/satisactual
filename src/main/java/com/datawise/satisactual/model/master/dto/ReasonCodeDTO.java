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
public class ReasonCodeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_reason")
    private String codReason;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_reason_desc")
    private String reasonDesc;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;

	@JsonProperty("flg_apply_to_rejections")
    private FlagYesNo applyToRejections;

	@JsonProperty("flg_apply_to_withdrawal")
    private FlagYesNo applyToWithdrawal;

	@JsonProperty("flg_apply_to_cancellaton")
    private FlagYesNo applyToCancellaton;

	@JsonProperty("flg_apply_to_approvals")
    private FlagYesNo applyToApprovals;

	@JsonProperty("flg_apply_to_waivers")
    private FlagYesNo applyToWaivers;
}
