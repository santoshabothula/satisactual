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
public class PinCodeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 8)
	@JsonProperty("cod_pin_code")
    private String codPinCode;

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_country")
    private String codCountry;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_state_code")
    private String stateCode;

    @NotBlank
    @Size(min = 1, max = 12)
	@JsonProperty("cod_district")
    private String codDistrict;

    @Size(min = 1, max = 12)
	@JsonProperty("txt_district_name")
    private String districtName;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_city_name")
    private String cityName;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_post_office_name")
    private String postOfficeName;

	@JsonProperty("num_longitude")
    private Double longitude;

	@JsonProperty("num_latitude")
    private Double latitude;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;

}
