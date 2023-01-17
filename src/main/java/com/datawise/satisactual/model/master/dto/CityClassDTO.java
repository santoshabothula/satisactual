package com.datawise.satisactual.model.master.dto;

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
public class CityClassDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
    @JsonProperty("cod_city_class")
    private String codCityClass;

    @Size(min = 1, max = 48)
    @JsonProperty("txt_city_class_desc")
    private String cityClassDesc;

    @JsonProperty("amt_mthly_rent_hni")
    private Double amtMthlyRentHni;

    @JsonProperty("amt_mthly_rent_affluent")
    private Double amtMthlyRentAffluent;

    @JsonProperty("amt_mthly_rent_emerging")
    private Double amtMthlyRentEmerging;

    @JsonProperty("amt_mthly_rent_mass")
    private Double amtMthlyRentMass;

    @JsonProperty("amt_mthly_exp_per_head_hni")
    private Double amtMthlyExpPerHeadHni;

    @JsonProperty("amt_mthly_exp_per_head_affluent")
    private Double amtMthlyExpPerHeadAffluent;

    @JsonProperty("amt_mthly_exp_per_head_emerging")
    private Double amtMthlyExpPerHeadEmerging;

    @JsonProperty("amt_mthly_exp_per_head_mass")
    private Double amtMthlyExpPerHeadMass;
}