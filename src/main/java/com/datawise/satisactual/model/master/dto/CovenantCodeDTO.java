package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CovenantCodeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_covenant")
    private String codCovenant;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_covenant_desc")
    private String covenantDesc;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_covenant_type")
    private String covenantType;

	@JsonProperty("txt_covenant_phrasing")
    private String covenantPhrasing;

	@JsonProperty("txt_remedial_action")
    private String remedialAction;

	@JsonProperty("num_days_remediation_grace")
    private Integer daysRemediationGrace;

	@JsonProperty("txt_penal_action")
    private String penalAction;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_covenant_key_ratio")
    private String covenantKeyRatio;

	@JsonProperty("num_min_value_default")
    private Double minValueDefault;

	@JsonProperty("num_max_value_default")
    private Double maxValueDefault;

    @Size(min = 1, max = 1)
	@JsonProperty("enu_freq_verification")
    private String freqVerification;

    @Size(min = 1, max = 255)
	@JsonProperty("txt_verification_method")
    private String verificationMethod;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_doc_type")
    private String docType;

    @Size(min = 1, max = 1)
	@JsonProperty("enu_doc_purpose")
    private String docPurpose;

}
