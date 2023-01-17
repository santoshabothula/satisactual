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
@Table(name = "mst_credit_officer_levels")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditOfficerLevelEntity extends MakerCheckerEntity {

    @EmbeddedId
    private CreditOfficerLevelEmbeddedKey id;
    
    @Column(name = "txt_credit_officer_level_desc")
    private String creditOfficerLevelDesc;

    @Column(name = "amt_max_approving_authority")
    private Double maxApprovingAuthority;

    @Column(name = "amt_single_approver_limit")
    private Double singleApproverLimit;

    @Column(name = "amt_annual_approval_limit")
    private Double annualApprovalLimit;

    @Column(name = "flg_default_value")
    private String defaultValue;

}