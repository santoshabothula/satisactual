package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditTypeDTO extends MakerCheckerDTO {

    @JsonProperty("cod_audit_type")
    private String codAuditType;

    @JsonProperty("cod_rec_status")
    private String codRecordStatus;

    @JsonProperty("txt_audit_type_desc")
    private String auditTypeDesc;

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
    private String defaultValue;
}
