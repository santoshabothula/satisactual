package com.datawise.satisactual.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class FieldSurveyRequest {

    @NotBlank
    @JsonProperty("id_campaign")
    private String idCampaign;

    @NotBlank
    @JsonProperty("id_campaign_wave")
    private String idCampaignWave;

    @JsonProperty("id_response")
    private String idResponse;

    @NotBlank(message = "Contact sequence should not be empty")
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

    @NotBlank(message = "Latitude Start should not be empty")
    @JsonProperty("num_latitude_start")
    private String numLatitudeStart;

    @NotBlank(message = "Latitude End should not be empty")
    @JsonProperty("num_latitude_end")
    private String numLatitudeEnd;

    @NotBlank(message = "Longitude End should not be empty")
    @JsonProperty("num_longitude_start")
    private String numLongitudeStart;

    @NotBlank(message = "Longitude End should not be empty")
    @JsonProperty("num_longitude_end")
    private String numLongitudeEnd;

    @NotBlank(message = "dat_time_survey_start should not be empty")
    @JsonProperty("dat_time_survey_start")
    private String datTimeSurveyStart;

    @NotBlank
    @JsonProperty("dat_time_survey_end")
    private String datTimeSurveyEnd;

    @JsonProperty("response_detail")
    private List<FieldSurveyResponseDetails> fieldSurveyResponseDetails;
}
