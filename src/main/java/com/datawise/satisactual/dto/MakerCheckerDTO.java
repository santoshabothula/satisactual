package com.datawise.satisactual.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
public class MakerCheckerDTO {

    @NotBlank
    @JsonProperty("txt_last_maker_id")
    private String lastMakerId;

    @JsonProperty("dat_last_maker")
    private Date lastMakerDateTime;

    @JsonProperty("txt_last_checker_id")
    private String lastCheckerId;

    @JsonProperty("dat_last_checker")
    private Date lastCheckerDateTime;
}
