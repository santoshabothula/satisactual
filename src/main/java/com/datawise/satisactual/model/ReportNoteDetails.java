package com.datawise.satisactual.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
@JsonIgnoreProperties
public class ReportNoteDetails {

    @JsonProperty("id_question")
    private BigInteger idQuestion;

    @JsonProperty("txt_section_name")
    private String txtSectionName;

    @JsonProperty("txt_author_id")
    private String txtAuthorId;

    @JsonProperty("txt_note")
    private String txtNote;

    @JsonProperty("dat_action_due")
    private String datActionDue;

    @JsonProperty("flg_include_in_report")
    private String flgIncludeInReport;

    @JsonProperty("flg_resolved")
    private String flgResolved;

    @JsonProperty("dat_time_resolved")
    private Date datTimeResolved;

    @JsonProperty("txt_resolution_note")
    private String txtResolutionNote;

    @JsonProperty("dat_time_added")
    private Date datTimeAdded;

    @JsonProperty("dat_time_last_edited")
    private Date datTimeLastEdited;

    @JsonProperty("num_question_page")
    private Integer numQuestionPage;

    @JsonProperty("num_question_sequence")
    private Integer numQuestionSequence;
}
