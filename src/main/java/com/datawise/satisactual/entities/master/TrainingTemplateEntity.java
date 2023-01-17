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
@Table(name = "mst_training_template")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingTemplateEntity extends MakerCheckerEntity {

    @EmbeddedId
    private TrainingTemplateEmbeddedKey id;

    @Column(name = "txt_training_desc")
    private String trainingDesc;

    @Column(name = "flg_cycle_linked")
    private String cycleLinked;
}