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
@Table(name = "mst_ins_claim_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InsClaimTypeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private InsClaimTypeEmbeddedKey id;

    @Column(name = "txt_claim_typ_desc")
    private String claimTypDesc;

    @Column(name = "enu_loss_type")
    private String lossType;

    @Column(name = "flg_default_value")
    private String defaultValue;

}