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
@Table(name = "mst_covenant_codes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CovenantCodeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private CovenantCodeEmbeddedKey id;

    @Column(name = "txt_covenant_desc")
    private String covenantDesc;

    @Column(name = "cod_covenant_type")
    private String covenantType;

    @Column(name = "txt_covenant_phrasing")
    private String covenantPhrasing;

    @Column(name = "txt_remedial_action")
    private String remedialAction;

    @Column(name = "num_days_remediation_grace")
    private Integer daysRemediationGrace;

    @Column(name = "txt_penal_action")
    private String penalAction;

    @Column(name = "cod_covenant_key_ratio")
    private String covenantKeyRatio;

    @Column(name = "num_min_value_default")
    private Double minValueDefault;

    @Column(name = "num_max_value_default")
    private Double maxValueDefault;

    @Column(name = "enu_freq_verification")
    private String freqVerification;

    @Column(name = "txt_verification_method")
    private String verificationMethod;

    @Column(name = "cod_doc_type")
    private String docType;

    @Column(name = "enu_doc_purpose")
    private String docPurpose;

}