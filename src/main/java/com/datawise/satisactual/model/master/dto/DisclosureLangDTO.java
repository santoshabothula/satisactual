package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DisclosureLangDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_disclosure")
    private String codDisclosure;

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_language")
    private String codLanguage;

	@JsonProperty("txt_disclosure_phrasing")
    private String disclosurePhrasing;

}
