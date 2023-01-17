package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MajorCityDTO extends BaseDTO {

    @NotNull
    @Size(min = 1, max = 8)
	@JsonProperty("cod_city")
    private String codCity;

    @NotNull
    @Size(min = 1, max = 8)
	@JsonProperty("cod_country")
    private String codCountry;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_state_code")
    private String stateCode;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_city_name")
    private String cityName;

	@JsonProperty("num_longitude_nw")
    private Double longitudeNw;

	@JsonProperty("num_latitude_nw")
    private Double latitudeNw;

	@JsonProperty("num_longitude_se")
    private Double longitudeSe;

	@JsonProperty("num_latitude_se")
    private Double latitudeSe;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_time_zone")
    private String timeZone;

	@JsonProperty("num_hours_gmt_offset")
    private Double hoursGmtOffset;

	@JsonProperty("flg_daylight_savings")
    private FlagYesNo daylightSavings;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_city_class")
    private String cityClass;
}
