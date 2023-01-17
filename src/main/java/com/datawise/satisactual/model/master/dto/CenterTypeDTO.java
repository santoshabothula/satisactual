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
public class CenterTypeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_center_type")
    private String codCenterType;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_center_type_desc")
    private String centerTypeDesc;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;

	@JsonProperty("num_min_center_members")
    private Integer minCenterMembers;

	@JsonProperty("num_max_center_members")
    private Integer maxCenterMembers;

	@JsonProperty("min_meetings_first_disburse")
    private Integer minMeetingsFirstDisburse;

	@JsonProperty("min_meetings_next_disburse")
    private Integer minMeetingsNextDisburse;

	@JsonProperty("enu_center_audit_freq")
    private String centerAuditFreq;
}
