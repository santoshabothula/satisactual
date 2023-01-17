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
public class FinRatioDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_ratio")
    private String codRatio;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_ratio_name")
    private String ratioName;

	@JsonProperty("flg_denom_prev_period")
    private FlagYesNo denomPrevPeriod;
}
