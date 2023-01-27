package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleModelDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_model")
    private String codModel;

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_make")
    private String codMake;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_model_desc")
    private String modelDesc;

	@JsonProperty("amt_onroad_cost")
    private Double onRoadCost;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
	@JsonProperty("dat_launched")
    private LocalDate launched;
}
