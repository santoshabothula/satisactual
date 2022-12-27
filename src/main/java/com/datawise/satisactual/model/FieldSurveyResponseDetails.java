package com.datawise.satisactual.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FieldSurveyResponseDetails {

    @NotBlank
    @JsonProperty("id_question")
    private String idQuestion;

    @NotBlank
    @JsonProperty("cod_option_selected")
    private String codOptionSelected;

    @JsonProperty("txt_question_response")
    private String txtQuestionResponse;

    @JsonProperty("num_rank")
    private String numRank;

    @JsonProperty("bin_recording_path")
    private String binRecordingPath;
}
