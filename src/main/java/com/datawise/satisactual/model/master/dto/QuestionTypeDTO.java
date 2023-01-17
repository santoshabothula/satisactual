package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionTypeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_question_type")
    private String codQuestionType;

    @NotBlank
    @Size(min = 1, max = 1)
	@JsonProperty("enu_base_question_type")
    private String baseQuestionType;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_question_type_desc")
    private String questionTypeDesc;

    @Size(min = 1, max = 1)
	@JsonProperty("enu_display_type")
    private String displayType;

	@JsonProperty("num_display_order")
    private Integer displayOrder;

	@JsonProperty("num_max_chars_response")
    private Integer maxCharsResponse;

    @Size(min = 1, max = 255)
	@JsonProperty("txt_response_format")
    private String responseFormat;

    @Size(min = 1, max = 9)
	@JsonProperty("enu_report_format")
    private String reportFormat;

    @Size(min = 1, max = 1)
	@JsonProperty("flg_av_recording_reqd")
    private String avRecordingReqd;

	@JsonProperty("flg_simple_question")
    private FlagYesNo simpleQuestion;
}
