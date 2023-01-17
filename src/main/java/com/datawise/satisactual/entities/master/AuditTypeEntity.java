package com.datawise.satisactual.entities.master;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mst_audit_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditTypeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private AuditTypeEmbeddedKey id;

    @Column(name = "txt_audit_type_desc")
    private String auditTypeDesc;

    @Column(name = "enu_audit_periodicity")
    private String auditPeriodicity;

    @Column(name = "num_days_audit")
    private Integer daysAudit;

    @Column(name = "num_days_audit_findings")
    private Integer daysAuditFindings;

    @Column(name = "num_days_audit_response")
    private Integer daysAuditResponse;

    @Column(name = "num_days_audit_review")
    private Integer daysAuditReview;

    @Column(name = "flg_default_value")
    private String defaultValue;
}
