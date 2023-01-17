package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingTemplateModuleDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_training_type")
    private String codTrainingType;

    @NotNull
	@JsonProperty("num_training_day")
    private Integer trainingDay;

    @NotNull
	@JsonProperty("num_day_session")
    private Integer daySession;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_session_topic")
    private String sessionTopic;

    @Size(min = 1, max = 255)
	@JsonProperty("txt_session_training_material")
    private String sessionTrainingMaterial;

    @Size(min = 1, max = 255)
	@JsonProperty("txt_session_training_audio_visual")
    private String sessionTrainingAudioVisual;

    @Size(min = 1, max = 255)
	@JsonProperty("txt_session_test_questions")
    private String sessionTestQuestions;

	@JsonProperty("num_max_score")
    private Integer maxScore;

	@JsonProperty("num_pass_score")
    private Integer passScore;
}
