package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegdEmployerDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 24)
	@JsonProperty("txt_employer_shortname")
    private String employerShortname;

    @NotBlank
	@JsonProperty("id_third_party_employer")
    private Long idThirdPartyEmployer;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_employer_category")
    private String employerCategory;

}
