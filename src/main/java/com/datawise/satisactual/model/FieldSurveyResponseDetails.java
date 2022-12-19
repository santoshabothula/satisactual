package com.datawise.satisactual.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FieldSurveyResponseDetails {

    @JsonProperty("id_question")
    private String idQuestion;

    @JsonProperty("cod_option_selected")
    private String codOptionSelected;

    @JsonProperty("txt_question_response")
    private String txtQuestionResponse;

    @JsonProperty("num_rank")
    private String numRank;

    @JsonProperty("bin_recording_path")
    private String binRecordingPath;
}
