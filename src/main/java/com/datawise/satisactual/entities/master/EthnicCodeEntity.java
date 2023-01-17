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
@Table(name = "mst_ethnic_codes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EthnicCodeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private EthnicCodeEmbeddedKey id;

    @Column(name = "txt_ethnicity_desc")
    private String ethnicityDesc;

    @Column(name = "flg_default_value")
    private String defaultValue;
}