package com.datawise.satisactual.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class ReportNoteRequest {

    @JsonProperty("pin_id_campaign")
    private Integer pinIdCampaign;

    @JsonProperty("pin_id_campaign_wave")
    private Integer pinIdCampaignWave;

    @JsonProperty("pin_id_question")
    private Integer pinIdQuestion;

    @JsonProperty("pin_txt_section_name")
    private String pinTxtSectionName;

    @JsonProperty("pin_txt_author_id")
    private String pinTxtAuthorId;

    @JsonProperty("pin_txt_note")
    private String pinTxtNote;

    @JsonProperty("pin_dat_action_due")
    private String pinDatActionDue;

    @JsonProperty("pin_flg_include_in_report")
    private String pinFlgIncludeInReport;

    @JsonProperty("pin_flg_resolved")
    private String pinFlgResolved;

    @JsonProperty("pin_txt_resolution_note")
    private String pinTxtResolutionNote;
}
