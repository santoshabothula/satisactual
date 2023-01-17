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
public class TxnCodeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_txn")
    private String codTxn;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_txn_short_desc")
    private String txnShortDesc;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_txn_long_desc")
    private String txnLongDesc;

	@JsonProperty("flg_auth_mandatory")
    private FlagYesNo authMandatory;

	@JsonProperty("flg_dual_key_mandatory")
    private FlagYesNo dualKeyMandatory;

    @Size(min = 1, max = 5)
	@JsonProperty("enu_txn_mode")
    private String txnMode;

}
