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
@Table(name = "mst_center_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CenterTypeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private CenterTypeEmbeddedKey id;

    @Column(name = "txt_center_type_desc")
    private String centerTypeDesc;

    @Column(name = "flg_default_value")
    private String defaultValue;

    @Column(name = "num_min_center_members")
    private Integer minCenterMembers;

    @Column(name = "num_max_center_members")
    private Integer maxCenterMembers;

    @Column(name = "min_meetings_first_disburse")
    private Integer minMeetingsFirstDisburse;

    @Column(name = "min_meetings_next_disburse")
    private Integer minMeetingsNextDisburse;

    @Column(name = "enu_center_audit_freq")
    private String centerAuditFreq;
}