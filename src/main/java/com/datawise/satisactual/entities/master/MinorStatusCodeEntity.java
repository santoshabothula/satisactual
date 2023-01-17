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
@Table(name = "mst_minor_status_codes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MinorStatusCodeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private MinorStatusCodeEmbeddedKey id;

    @Column(name = "txt_status_desc")
    private String statusDesc;

    @Column(name = "enu_entity_type")
    private String entityType;

    @Column(name = "flg_apply_to_individual")
    private String applyToIndividual;

    @Column(name = "flg_apply_to_organisation")
    private String applyToOrganisation;

    @Column(name = "cod_txn")
    private String txn;

}