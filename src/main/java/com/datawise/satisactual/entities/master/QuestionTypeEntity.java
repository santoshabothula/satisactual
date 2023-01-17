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
@Table(name = "mst_question_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionTypeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private QuestionTypeEmbeddedKey id;

    @Column(name = "txt_question_type_desc")
    private String questionTypeDesc;

    @Column(name = "enu_display_type")
    private String displayType;

    @Column(name = "num_display_order")
    private Integer displayOrder;

    @Column(name = "num_max_chars_response")
    private Integer maxCharsResponse;

    @Column(name = "txt_response_format")
    private String responseFormat;

    @Column(name = "enu_report_format")
    private String reportFormat;

    @Column(name = "flg_av_recording_reqd")
    private String avRecordingReqd;

    @Column(name = "flg_simple_question")
    private String simpleQuestion;

}