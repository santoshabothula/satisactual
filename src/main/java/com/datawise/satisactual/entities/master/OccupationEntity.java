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
@Table(name = "mst_occupations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OccupationEntity extends MakerCheckerEntity {

    @EmbeddedId
    private OccupationEmbeddedKey id;

    @Column(name = "txt_occupation_desc")
    private String occupationDesc;

    @Column(name = "enu_occupation_type")
    private String occupationType;

    @Column(name = "flg_psl_qualified")
    private String pslQualified;

    @Column(name = "cod_priority_sector_category")
    private String prioritySectorCategory;

    @Column(name = "cod_primary_income_src")
    private String primaryIncomeSrc;

    @Column(name = "flg_default_value")
    private String defaultValue;

}