package com.datawise.satisactual.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class FieldSurveyRequest {

    @JsonProperty("id_campaign")
    private String idCampaign;

    @JsonProperty("id_campaign_wave")
    private String idCampaignWave;

    @JsonProperty("id_response")
    private String idResponse;

    @JsonProperty("num_contact_sequence")
    private String numContactSequence;

    @JsonProperty("cod_language")
    private String codLanguage;

    @JsonProperty("id_contact_list")
    private String idContactList;

    @JsonProperty("num_list_item")
    private String numListItem;

    @JsonProperty("txt_survey_conducted_by")
    private String txtSurveyConductedBy;

    @JsonProperty("txt_device_id")
    private String txtDeviceId;

    @JsonProperty("txt_responder_name")
    private String txtResponderName;

    @JsonProperty("num_responder_age")
    private String numResponderAge;

    @JsonProperty("enu_responder_gender")
    private String enuResponderGender;

    @JsonProperty("flg_anonymous_response")
    private String flgAnonymousResponse;

    @JsonProperty("flg_allow_clarif_contact")
    private String flgAllowCLARIfContact;

    @JsonProperty("num_latitude_start")
    private String numLatitudeStart;

    @JsonProperty("num_latitude_end")
    private String numLatitudeEnd;

    @JsonProperty("num_longitude_start")
    private String numLongitudeStart;

    @JsonProperty("num_longitude_end")
    private String numLongitudeEnd;

    @JsonProperty("dat_time_survey_start")
    private String datTimeSurveyStart;

    @JsonProperty("dat_time_survey_end")
    private String datTimeSurveyEnd;

    @JsonProperty("response_detail")
    private List<FieldSurveyResponseDetails> fieldSurveyResponseDetails;
}
