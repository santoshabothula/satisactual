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
@Table(name = "mst_reason_codes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReasonCodeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private ReasonCodeEmbeddedKey id;

    @Column(name = "txt_reason_desc")
    private String reasonDesc;

    @Column(name = "flg_default_value")
    private String defaultValue;

    @Column(name = "flg_apply_to_rejections")
    private String applyToRejections;

    @Column(name = "flg_apply_to_withdrawal")
    private String applyToWithdrawal;

    @Column(name = "flg_apply_to_cancellaton")
    private String applyToCancellaton;

    @Column(name = "flg_apply_to_approvals")
    private String applyToApprovals;

    @Column(name = "flg_apply_to_waivers")
    private String applyToWaivers;
}