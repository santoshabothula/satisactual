package com.datawise.satisactual.entities.master;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionTypeEmbeddedKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cod_question_type")
    private String codQuestionType;

    @Column(name = "enu_base_question_type")
    private String baseQuestionType;

    @Column(name = "cod_rec_status")
    private String codRecordStatus;
}