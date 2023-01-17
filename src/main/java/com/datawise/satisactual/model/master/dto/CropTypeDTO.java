package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CropTypeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_crop_type")
    private String codCropType;

    @Size(min = 1, max = 6)
	@JsonProperty("enu_crop_class")
    private String cropClass;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_crop_type_desc")
    private String cropTypeDesc;

	@JsonProperty("num_kgs_yield_per_acre")
    private Integer kgsYieldPerAcre;

	@JsonProperty("amt_purch_price_per_kg")
    private Double purchPricePerKg;

	@JsonProperty("amt_input_costs_per_acre")
    private Double inputCostsPerAcre;

	@JsonProperty("max_harvests_per_year")
    private Integer harvestsPerYear;

	@JsonProperty("num_mths_crop_cycle")
    private Integer mthsCropCycle;

	@JsonProperty("num_days_shelf_life")
    private Integer daysShelfLife;
}
