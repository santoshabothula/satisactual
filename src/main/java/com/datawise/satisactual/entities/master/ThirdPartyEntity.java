package com.datawise.satisactual.entities.master;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "mst_third_parties")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThirdPartyEntity extends MakerCheckerEntity {

    @EmbeddedId
    private ThirdPartyEmbeddedKey id;
    
    @Column(name = "cod_third_party_type")
    private String thirdPartyType;

    @Column(name = "txt_third_party_name")
    private String thirdPartyName;

    @Column(name = "txt_third_party_short_name")
    private String thirdPartyShortName;

    @Column(name = "flg_credit_bureau")
    private String creditBureau;

    @Column(name = "flg_vendor_agency")
    private String vendorAgency;

    @Column(name = "flg_builder_developer")
    private String builderDeveloper;

    @Column(name = "flg_regulator")
    private String regulator;

    @Column(name = "flg_employer")
    private String employer;

    @Column(name = "flg_client_entity")
    private String clientEntity;

    @Column(name = "flg_authentication_agency")
    private String authenticationAgency;

    @Column(name = "flg_distributor_agency")
    private String distributorAgency;

    @Column(name = "flg_auditor")
    private String auditor;

    @Column(name = "flg_lawyer")
    private String lawyer;

    @Column(name = "flg_clearing_house")
    private String clearingHouse;

    @Column(name = "flg_insurer")
    private String insurer;

    @Column(name = "flg_accredited_university")
    private String accreditedUniversity;

    @Column(name = "txt_our_id")
    private String ourId;

    @Column(name = "flg_msg_xchg_available")
    private String msgXchgAvailable;

    @Column(name = "txt_msg_xchg_ip_address")
    private String msgXchgIpAddress;

    @Column(name = "txt_msg_xchg_user_id")
    private String msgXchgUserId;

    @Column(name = "txt_msg_xchg_signature")
    private String msgXchgSignature;

    @Column(name = "txt_msg_send_format")
    private String msgSendFormat;

    @Column(name = "txt_msg_recv_format")
    private String msgRecvFormat;

    @Column(name = "txt_file_send_format")
    private String fileSendFormat;

    @Column(name = "txt_file_recv_format")
    private String fileRecvFormat;

    @Column(name = "num_min_score_possible")
    private Integer minScorePossible;

    @Column(name = "num_max_score_possible")
    private Integer maxScorePossible;

    @Column(name = "txt_principal_contact_name")
    private String principalContactName;

    @Column(name = "txt_addr_line1")
    private String addrLine1;

    @Column(name = "txt_addr_line2")
    private String addrLine2;

    @Column(name = "txt_addr_line3")
    private String addrLine3;

    @Column(name = "txt_addr_city")
    private String addrCity;

    @Column(name = "txt_addr_pin")
    private String addrPin;

    @Column(name = "cod_address_state")
    private String addressState;

    @Column(name = "cod_addr_country")
    private String addrCountry;

    @Column(name = "dat_addr_effective")
    private LocalDate addrEffective;

    @Column(name = "txt_phone_num")
    private String phoneNum;

    @Column(name = "txt_email_id")
    private String emailId;

    @Column(name = "txt_org_url")
    private String orgUrl;

    @Column(name = "txt_escalation_1_name")
    private String escalation1Name;

    @Column(name = "txt_escalation_1_phone")
    private String escalation1Phone;

    @Column(name = "txt_escalation_1_email")
    private String escalation1Email;

    @Column(name = "txt_escalation_1_title")
    private String escalation1Title;

    @Column(name = "txt_escalation_2_name")
    private String escalation2Name;

    @Column(name = "txt_escalation_2_phone")
    private String escalation2Phone;

    @Column(name = "txt_escalation_2_email")
    private String escalation2Email;

    @Column(name = "txt_escalation_2_title")
    private String escalation2Title;

    @Column(name = "txt_registration_num")
    private String registrationNum;

    @Column(name = "dat_registration")
    private LocalDate registration;

    @Column(name = "num_gl_acct")
    private Long glAcct;

    @Column(name = "bin_third_party_logo")
    private String binThirdPartyLogo;

}