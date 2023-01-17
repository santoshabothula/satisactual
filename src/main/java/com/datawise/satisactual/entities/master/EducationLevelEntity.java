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
@Table(name = "mst_education_levels")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EducationLevelEntity extends MakerCheckerEntity {

    @EmbeddedId
    private EducationLevelEmbeddedKey id;

    @Column(name = "txt_education_level_desc")
    private String educationLevelDesc;

    @Column(name = "flg_default_value")
    private String defaultValue;

}