package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocTypeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_doc_type")
    private String codDocType;

    @NotBlank
    @Size(min = 1, max = 1)
	@JsonProperty("enu_doc_purpose")
    private String enuDocPurpose;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_doc_type_desc")
    private String docTypeDesc;

	@JsonProperty("num_validity_days")
    private Integer validityDays;

	@JsonProperty("flg_check_issue_date")
    private FlagYesNo checkIssueDate;

	@JsonProperty("flg_check_expiry_date")
    private FlagYesNo checkExpiryDate;

	@JsonProperty("flg_accept_as_id_proof")
    private FlagYesNo acceptAsIdProof;

	@JsonProperty("flg_accept_as_addr_proof")
    private FlagYesNo acceptAsAddrProof;

	@JsonProperty("flg_accept_as_income_proof")
    private FlagYesNo acceptAsIncomeProof;

	@JsonProperty("num_points_value")
    private Integer pointsValue;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_docnum_format")
    private String docnumFormat;

	@JsonProperty("flg_accept_for_organisation")
    private FlagYesNo acceptForOrganisation;

	@JsonProperty("flg_accept_for_individual")
    private FlagYesNo acceptForIndividual;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;

	@JsonProperty("id_third_party_issuer")
    private Long idThirdPartyIssuer;

	@JsonProperty("flg_auto_verify_available")
    private FlagYesNo autoVerifyAvailable;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_auto_verify_ip_address")
    private String autoVerifyIpAddress;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_auto_verify_user_id")
    private String autoVerifyUserId;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_auto_verify_signature")
    private String autoVerifySignature;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_autoscan_api_code")
    private String autoscanApiCode;

}
