package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeZoneDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_time_zone")
    private String codTimeZone;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_time_zone_name")
    private String timeZoneName;

	@JsonProperty("num_hours_gmt_offset")
    private Double hoursGmtOffset;

	@JsonProperty("flg_daylight_savings")
    private FlagYesNo daylightSavings;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;
}
