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
@Table(name = "mst_panchayat_codes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PanchayatCodeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private PanchayatCodeEmbeddedKey id;

    @Column(name = "txt_panchayat_name")
    private String panchayatName;

    @Column(name = "flg_default_value")
    private String defaultValue;
}