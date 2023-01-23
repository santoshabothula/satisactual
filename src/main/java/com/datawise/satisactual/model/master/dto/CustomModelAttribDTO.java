package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomModelAttribDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_scoring_model")
    private String codScoringModel;

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_attribute")
    private String codAttribute;

    @NotBlank
    @Size(min = 1, max = 24)
	@JsonProperty("txt_attrib_value_min")
    private String attribValueMin;

    @NotBlank
    @Size(min = 1, max = 24)
	@JsonProperty("txt_attrib_value_max")
    private String attribValueMax;

	@JsonProperty("num_response_score")
    private Integer responseScore;

	@JsonProperty("num_multiplier")
    private Float multiplier;

	@JsonProperty("enu_exponential_factor")
    private String exponentialFactor;

	@JsonProperty("enu_transform_factor")
    private String transformFactor;

}
