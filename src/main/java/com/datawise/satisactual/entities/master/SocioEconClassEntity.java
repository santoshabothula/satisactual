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
@Table(name = "mst_socio_econ_class")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocioEconClassEntity extends MakerCheckerEntity {

    @EmbeddedId
    private SocioEconClassEmbeddedKey id;

    @Column(name = "txt_socio_econ_desc")
    private String socioEconDesc;

    @Column(name = "flg_default_value")
    private String defaultValue;

    @Column(name = "flg_psl_qualified")
    private String pslQualified;

    @Column(name = "cod_priority_sector_category")
    private String prioritySectorCategory;

}