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
public class TrainingTemplateModuleEmbeddedKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cod_training_type")
    private String codTrainingType;

    @Column(name = "num_training_day")
    private Integer trainingDay;

    @Column(name = "num_day_session")
    private Integer daySession;

    @Column(name = "cod_rec_status")
    private String codRecordStatus;
}