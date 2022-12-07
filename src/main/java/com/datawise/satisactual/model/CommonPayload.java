package com.datawise.satisactual.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class CommonPayload {

    @JsonProperty("pin_txt_login_id")
    private String pinTxtLoginId;

    @JsonProperty("pin_id_campaign")
    private String pinIdCampaign;

    @JsonProperty("pin_id_campaign_wave")
    private String pinIdCampaignWave;

}
