package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinRatiosIndBenchmarkDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_ratio")
    private String codRatio;

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_industry_grp")
    private String codIndustryGrp;

	@JsonProperty("num_value_min")
    private Float valueMin;

	@JsonProperty("num_value_max")
    private Float valueMax;
}
