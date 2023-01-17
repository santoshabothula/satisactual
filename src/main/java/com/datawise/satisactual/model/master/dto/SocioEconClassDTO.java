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
public class SocioEconClassDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_socio_econ_class")
    private String codSocioEconClass;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_socio_econ_desc")
    private String socioEconDesc;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;

	@JsonProperty("flg_psl_qualified")
    private FlagYesNo pslQualified;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_priority_sector_category")
    private String prioritySectorCategory;

}
