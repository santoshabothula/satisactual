package com.datawise.satisactual.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class ReportRequest {

    @JsonProperty("pin_id_campaign")
    int campaignId;

    @JsonProperty ("pin_id_wave")
    int waveId;

    @JsonProperty ("pin_dat_start")
    String datStart;

    @JsonProperty ("pin_dat_end")
    String datEnd;

    @JsonProperty  ("pflg_include_cover")
    String includeCover;

    @JsonProperty ("pflg_response_summ")
    String responseSum;

    @JsonProperty ("pflg_not_qualified_summ")
    String notQualifiedSum;

    @JsonProperty ("pflg_outcome_summ")
    String outcomeSum;

    @JsonProperty ("pflg_include_not_selected")
    String includeNotSelected;

    @JsonProperty ("pflg_include_rating_detail")
    String includeRatingDetail;

    @JsonProperty ("pflg_include_sample_survey")
    String includeSampleSurvey;

    @JsonProperty ("pflg_show_rating_groups")
    String showRatingGroups;

    @JsonProperty ("pnum_low_rating_below")
    int lowRatingBelow;

    @JsonProperty ("pnum_high_rating_above")
    int highRatingAbove;

    @JsonProperty ("pin_txt_curr_user")
    String txtCurrUser;
}
