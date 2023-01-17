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
@Table(name = "mst_district_codes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DistrictCodeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private DistrictCodeEmbeddedKey id;

    @Column(name = "txt_district_name")
    private String districtName;

    @Column(name = "flg_default_value")
    private String defaultValue;

}