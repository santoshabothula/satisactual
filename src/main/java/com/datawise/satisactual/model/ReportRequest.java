package com.datawise.satisactual.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class ReportRequest {

    @JsonProperty("pin_id_campaign")
    private int campaignId;

    @JsonProperty ("pin_id_wave")
    private int waveId;

    @JsonProperty ("pin_dat_start")
    private String datStart;

    @JsonProperty ("pin_dat_end")
    private String datEnd;

    @JsonProperty  ("pflg_include_cover")
    private String includeCover;

    @JsonProperty ("pflg_response_summ")
    private String responseSum;

    @JsonProperty ("pflg_not_qualified_summ")
    private String notQualifiedSum;

    @JsonProperty ("pflg_outcome_summ")
    private String outcomeSum;

    @JsonProperty ("pflg_include_not_selected")
    private String includeNotSelected;

    @JsonProperty ("pflg_include_rating_detail")
    private String includeRatingDetail;

    @JsonProperty ("pflg_include_sample_survey")
    private String includeSampleSurvey;

    @JsonProperty ("pflg_show_rating_groups")
    private String showRatingGroups;

    @JsonProperty ("pnum_low_rating_below")
    private int lowRatingBelow;

    @JsonProperty ("pnum_high_rating_above")
    private int highRatingAbove;

    @JsonProperty ("pin_txt_curr_user")
    private String txtCurrUser;

    @JsonProperty("pin_enu_page_orientation")
    private String pinEnuPageOrientation;

    @JsonProperty("pin_cod_report_template")
    private String pinCodReportTemplate;
}
