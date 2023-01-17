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
@Table(name = "mst_prod_disclosures")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdDisclosureEntity extends MakerCheckerEntity {

    @EmbeddedId
    private ProdDisclosureEmbeddedKey id;
    
    @Column(name = "num_sequence")
    private Integer sequence;

    @Column(name = "flg_all_applicants_mandatory")
    private String allApplicantsMandatory;

    @Column(name = "flg_guarantors_mandatory")
    private String guarantorsMandatory;
}