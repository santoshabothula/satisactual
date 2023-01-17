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
@Table(name = "mst_priority_sector_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrioritySectorCategoryEntity extends MakerCheckerEntity {

    @EmbeddedId
    private PrioritySectorCategoryEmbeddedKey id;

    @Column(name = "txt_priority_sector_desc")
    private String prioritySectorDesc;

    @Column(name = "max_loan_amt_to_qualify")
    private Double loanAmtToQualify;

    @Column(name = "flg_default_value")
    private String defaultValue;

    @Column(name = "cod_document")
    private String document;

}