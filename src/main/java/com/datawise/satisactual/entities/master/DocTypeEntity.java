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
@Table(name = "mst_doc_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocTypeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private DocTypeEmbeddedKey id;

    @Column(name = "txt_doc_type_desc")
    private String docTypeDesc;

    @Column(name = "num_validity_days")
    private Integer validityDays;

    @Column(name = "flg_check_issue_date")
    private String checkIssueDate;

    @Column(name = "flg_check_expiry_date")
    private String checkExpiryDate;

    @Column(name = "flg_accept_as_id_proof")
    private String acceptAsIdProof;

    @Column(name = "flg_accept_as_addr_proof")
    private String acceptAsAddrProof;

    @Column(name = "flg_accept_as_income_proof")
    private String acceptAsIncomeProof;

    @Column(name = "num_points_value")
    private Integer pointsValue;

    @Column(name = "txt_docnum_format")
    private String docnumFormat;

    @Column(name = "flg_accept_for_organisation")
    private String acceptForOrganisation;

    @Column(name = "flg_accept_for_individual")
    private String acceptForIndividual;

    @Column(name = "flg_default_value")
    private String defaultValue;

    @Column(name = "id_third_party_issuer")
    private Long idThirdPartyIssuer;

    @Column(name = "flg_auto_verify_available")
    private String autoVerifyAvailable;

    @Column(name = "txt_auto_verify_ip_address")
    private String autoVerifyIpAddress;

    @Column(name = "txt_auto_verify_user_id")
    private String autoVerifyUserId;

    @Column(name = "txt_auto_verify_signature")
    private String autoVerifySignature;

    @Column(name = "txt_autoscan_api_code")
    private String autoscanApiCode;

}