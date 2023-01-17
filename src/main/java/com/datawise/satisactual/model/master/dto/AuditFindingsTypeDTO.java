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
public class AuditFindingsTypeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
    @JsonProperty("cod_audit_finding_type")
    private String codAuditFindingType;

    @NotBlank
    @Size(min = 1, max = 48)
    @JsonProperty("txt_audit_finding_type_desc")
    private String auditFindingTypeDesc;

    @NotBlank
    @JsonProperty("flg_corrective_action_reqd")
    private FlagYesNo correctiveActionReqd;

    @NotBlank
    @JsonProperty("flg_preventive_action_reqd")
    private FlagYesNo preventiveActionReqd;

    @NotBlank
    @JsonProperty("flg_adverse_observation")
    private FlagYesNo adverseObservation;

    @NotBlank
    @JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;
}
