package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LivestockDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_livestock")
    private String codLivestock;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_livestock_desc")
    private String livestockDesc;

	@JsonProperty("amt_yield_per_animal")
    private Double yieldPerAnimal;

	@JsonProperty("amt_input_cost_per_animal")
    private Double inputCostPerAnimal;
}
