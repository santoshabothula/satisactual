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
public class AuditTypeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
    @JsonProperty("cod_audit_type")
    private String codAuditType;

    @NotBlank
    @Size(min = 1, max = 48)
    @JsonProperty("txt_audit_type_desc")
    private String auditTypeDesc;

    @Size(min = 1, max = 1)
    @JsonProperty("enu_audit_periodicity")
    private String auditPeriodicity;

    @JsonProperty("num_days_audit")
    private Integer daysAudit;

    @JsonProperty("num_days_audit_findings")
    private Integer daysAuditFindings;

    @JsonProperty("num_days_audit_response")
    private Integer daysAuditResponse;

    @JsonProperty("num_days_audit_review")
    private Integer daysAuditReview;

    @JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;
}
