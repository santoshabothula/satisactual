package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdDisclosureDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_disclosure")
    private String codDisclosure;

    @NotBlank
    @Size(min = 1, max = 4)
    @JsonProperty("cod_product")
    private String codProduct;

	@JsonProperty("num_sequence")
    private Integer sequence;

	@JsonProperty("flg_all_applicants_mandatory")
    private FlagYesNo allApplicantsMandatory;

	@JsonProperty("flg_guarantors_mandatory")
    private FlagYesNo guarantorsMandatory;
}
