package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditFindingsTypeDTO extends MakerCheckerDTO {

    @NotBlank
    @JsonProperty("cod_audit_finding_type")
    private String codAuditFindingType;

    @NotBlank
    @JsonProperty("cod_rec_status")
    private String codRecordStatus;

    @NotBlank
    @Size(min = 1, max = 24)
    @JsonProperty("txt_audit_finding_type_desc")
    private String auditFindingTypeDesc;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("flg_corrective_action_reqd")
    private String correctiveActionReqd;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("flg_preventive_action_reqd")
    private String preventiveActionReqd;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("flg_adverse_observation")
    private String adverseObservation;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("flg_default_value")
    private String defaultValue;
}
