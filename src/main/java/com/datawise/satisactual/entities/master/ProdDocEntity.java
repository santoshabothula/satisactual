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
@Table(name = "mst_prod_docs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdDocEntity extends MakerCheckerEntity {

    @EmbeddedId
    private ProdDocEmbeddedKey id;

    @Column(name = "flg_acct_opening_doc")
    private String acctOpeningDoc;

    @Column(name = "num_days_from_acct_opening")
    private Integer daysFromAcctOpening;

    @Column(name = "flg_renewal")
    private String renewal;

    @Column(name = "enu_post_disb_resubmit_freq")
    private String postDisbResubmitFreq;

    @Column(name = "flg_mandatory")
    private String mandatory;
}