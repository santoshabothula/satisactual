package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IntRateDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_interest_rate")
    private String codInterestRate;

    @NotNull
	@JsonProperty("dat_effective")
    private LocalDate datEffective;

	@JsonProperty("rat_interest")
    private Double ratInterest;

}
