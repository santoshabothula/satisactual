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
@Table(name = "mst_loan_purposes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanPurposeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private LoanPurposeEmbeddedKey id;
    
    @Column(name = "txt_loan_purpose_desc")
    private String loanPurposeDesc;

    @Column(name = "cod_linked_coll_type")
    private String linkedCollType;

    @Column(name = "flg_education")
    private String education;

    @Column(name = "flg_default_value")
    private String defaultValue;

    @Column(name = "flg_psl_qualified")
    private String pslQualified;

    @Column(name = "cod_priority_sector_category")
    private String prioritySectorCategory;
}