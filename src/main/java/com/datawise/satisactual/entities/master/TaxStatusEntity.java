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
@Table(name = "mst_tax_status")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaxStatusEntity extends MakerCheckerEntity {

    @EmbeddedId
    private TaxStatusEmbeddedKey id;

    @Column(name = "txt_tax_status_desc")
    private String taxStatusDesc;

    @Column(name = "flg_apply_to_individual")
    private String applyToIndividual;

    @Column(name = "flg_apply_to_organisation")
    private String applyToOrganisation;
}