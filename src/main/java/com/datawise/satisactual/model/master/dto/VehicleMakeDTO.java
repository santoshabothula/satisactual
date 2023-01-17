package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleMakeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_make")
    private String codMake;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_make_desc")
    private String makeDesc;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_manufacturer_name")
    private String manufacturerName;
}
