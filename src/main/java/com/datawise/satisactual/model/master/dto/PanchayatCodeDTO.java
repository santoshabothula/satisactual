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
public class PanchayatCodeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 12)
	@JsonProperty("cod_panchayat")
    private String codPanchayat;

    @NotBlank
    @Size(min = 1, max = 12)
	@JsonProperty("cod_block")
    private String codBlock;

    @NotBlank
    @Size(min = 1, max = 12)
	@JsonProperty("cod_district")
    private String codDistrict;

    @NotBlank
    @Size(min = 1, max = 12)
	@JsonProperty("cod_state")
    private String codState;

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_country")
    private String codCountry;

	@JsonProperty("txt_panchayat_name")
    private String panchayatName;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;
}
