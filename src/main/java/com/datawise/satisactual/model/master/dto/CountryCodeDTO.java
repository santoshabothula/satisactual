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
public class CountryCodeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_country")
    private String codCountry;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_country_name")
    private String countryName;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_nationality_name")
    private String nationalityName;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_country_alternative")
    private String countryAlternative;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_phone_code")
    private String phoneCode;

	@JsonProperty("num_hours_gmt_offset")
    private Double hoursGmtOffset;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;
}
