package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdDocDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_product")
    private String codProduct;

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_doc_type")
    private String codDocType;

    @NotBlank
    @Size(min = 1, max = 1)
	@JsonProperty("enu_doc_purpose")
    private String docPurpose;

	@JsonProperty("flg_acct_opening_doc")
    private FlagYesNo acctOpeningDoc;

	@JsonProperty("num_days_from_acct_opening")
    private Integer daysFromAcctOpening;

	@JsonProperty("flg_renewal")
    private FlagYesNo renewal;

    @Size(min = 1, max = 1)
	@JsonProperty("enu_post_disb_resubmit_freq")
    private String postDisbResubmitFreq;

	@JsonProperty("flg_mandatory")
    private FlagYesNo mandatory;
}
