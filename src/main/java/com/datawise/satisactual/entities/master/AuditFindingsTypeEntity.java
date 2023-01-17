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
@Table(name = "mst_audit_findings_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditFindingsTypeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private AuditFindingsTypeEmbeddedKey id;

    @Column(name = "txt_audit_finding_type_desc")
    private String auditFindingTypeDesc;

    @Column(name = "flg_corrective_action_reqd")
    private String correctiveActionReqd;

    @Column(name = "flg_preventive_action_reqd")
    private String preventiveActionReqd;

    @Column(name = "flg_adverse_observation")
    private String adverseObservation;

    @Column(name = "flg_default_value")
    private String defaultValue;
}
