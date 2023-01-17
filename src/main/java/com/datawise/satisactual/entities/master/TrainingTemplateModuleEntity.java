package com.datawise.satisactual.entities.master;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mst_training_template_modules")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingTemplateModuleEntity extends MakerCheckerEntity {

    @EmbeddedId
    private TrainingTemplateModuleEmbeddedKey id;

    @Column(name = "txt_session_topic")
    private String sessionTopic;

    @Column(name = "txt_session_training_material")
    private String sessionTrainingMaterial;

    @Column(name = "txt_session_training_audio_visual")
    private String sessionTrainingAudioVisual;

    @Column(name = "txt_session_test_questions")
    private String sessionTestQuestions;

    @Column(name = "num_max_score")
    private Integer maxScore;

    @Column(name = "num_pass_score")
    private Integer passScore;
}