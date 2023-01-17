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
public class IncomeSrcDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_income_src")
    private String codIncomeSrc;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_income_src_desc")
    private String incomeSrcDesc;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;

	@JsonProperty("num_max_debt_cover_verified")
    private Integer maxDebtCoverVerified;

	@JsonProperty("num_max_debt_cover")
    private Integer maxDebtCover;

    @Size(min = 1, max = 1)
	@JsonProperty("enu_addl_details_type")
    private String addlDetailsType;
}
