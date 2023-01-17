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
@Table(name = "mst_religion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReligionEntity extends MakerCheckerEntity {

    @EmbeddedId
    private ReligionEmbeddedKey id;

    @Column(name = "txt_religion_desc")
    private String religionDesc;

    @Column(name = "flg_default_value")
    private String defaultValue;

}