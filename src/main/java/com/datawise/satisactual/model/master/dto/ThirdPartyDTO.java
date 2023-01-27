package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThirdPartyDTO extends BaseDTO {

    @NotNull
	@JsonProperty("id_third_party")
    private Long idThirdParty;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_third_party_type")
    private String thirdPartyType;

    @Size(min = 1, max = 96)
	@JsonProperty("txt_third_party_name")
    private String thirdPartyName;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_third_party_short_name")
    private String thirdPartyShortName;

	@JsonProperty("flg_credit_bureau")
    private FlagYesNo creditBureau;

	@JsonProperty("flg_vendor_agency")
    private FlagYesNo vendorAgency;

	@JsonProperty("flg_builder_developer")
    private FlagYesNo builderDeveloper;

	@JsonProperty("flg_regulator")
    private FlagYesNo regulator;

	@JsonProperty("flg_employer")
    private FlagYesNo employer;

	@JsonProperty("flg_client_entity")
    private FlagYesNo clientEntity;

	@JsonProperty("flg_authentication_agency")
    private FlagYesNo authenticationAgency;

	@JsonProperty("flg_distributor_agency")
    private FlagYesNo distributorAgency;

	@JsonProperty("flg_auditor")
    private FlagYesNo auditor;

	@JsonProperty("flg_lawyer")
    private FlagYesNo lawyer;

	@JsonProperty("flg_clearing_house")
    private FlagYesNo clearingHouse;

	@JsonProperty("flg_insurer")
    private FlagYesNo insurer;

	@JsonProperty("flg_accredited_university")
    private FlagYesNo accreditedUniversity;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_our_id")
    private String ourId;

	@JsonProperty("flg_msg_xchg_available")
    private FlagYesNo msgXchgAvailable;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_msg_xchg_ip_address")
    private String msgXchgIpAddress;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_msg_xchg_user_id")
    private String msgXchgUserId;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_msg_xchg_signature")
    private String msgXchgSignature;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_msg_send_format")
    private String msgSendFormat;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_msg_recv_format")
    private String msgRecvFormat;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_file_send_format")
    private String fileSendFormat;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_file_recv_format")
    private String fileRecvFormat;

	@JsonProperty("num_min_score_possible")
    private Integer minScorePossible;

	@JsonProperty("num_max_score_possible")
    private Integer maxScorePossible;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_principal_contact_name")
    private String principalContactName;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_addr_line1")
    private String addrLine1;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_addr_line2")
    private String addrLine2;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_addr_line3")
    private String addrLine3;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_addr_city")
    private String addrCity;

    @Size(min = 1, max = 8)
	@JsonProperty("txt_addr_pin")
    private String addrPin;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_address_state")
    private String addressState;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_addr_country")
    private String addrCountry;

	@JsonProperty("dat_addr_effective")
    private LocalDate addrEffective;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_phone_num")
    private String phoneNum;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_email_id")
    private String emailId;

    @Size(min = 1, max = 96)
	@JsonProperty("txt_org_url")
    private String orgUrl;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_escalation_1_name")
    private String escalation1Name;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_escalation_1_phone")
    private String escalation1Phone;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_escalation_1_email")
    private String escalation1Email;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_escalation_1_title")
    private String escalation1Title;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_escalation_2_name")
    private String escalation2Name;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_escalation_2_phone")
    private String escalation2Phone;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_escalation_2_email")
    private String escalation2Email;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_escalation_2_title")
    private String escalation2Title;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_registration_num")
    private String registrationNum;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
	@JsonProperty("dat_registration")
    private LocalDate registration;

	@JsonProperty("num_gl_acct")
    private Long glAcct;

    @Size(min = 1, max = 255)
	@JsonProperty("bin_third_party_logo")
    private String binThirdPartyLogo;
}
