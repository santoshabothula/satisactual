package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IntRateCodeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_interest_rate")
    private String codInterestRate;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_rate_desc")
    private String rateDesc;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_base_rate")
    private String baseRate;

	@JsonProperty("rat_interest_offset")
    private Double ratInterestOffset;
}
