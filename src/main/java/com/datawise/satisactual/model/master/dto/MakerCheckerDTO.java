package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class MakerCheckerDTO {

    @Size(min = 1, max = 48)
    @JsonProperty("txt_last_maker_id")
    private String lastMakerId;

    @JsonProperty("dat_last_maker")
    private LocalDateTime lastMakerDateTime;

    @Size(min = 1, max = 48)
    @JsonProperty("txt_last_checker_id")
    private String lastCheckerId;

    @JsonProperty("dat_last_checker")
    private LocalDateTime lastCheckerDateTime;
}
