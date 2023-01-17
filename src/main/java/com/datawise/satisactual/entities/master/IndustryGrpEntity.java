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
@Table(name = "mst_industry_grp")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IndustryGrpEntity extends MakerCheckerEntity {

    @EmbeddedId
    private IndustryGrpEmbeddedKey id;
    
    @Column(name = "txt_industry_name")
    private String industryName;

    @Column(name = "cod_parent_industry_grp")
    private String parentIndustryGrp;

    @Column(name = "txt_industry_sic_code")
    private String industrySicCode;

    @Column(name = "txt_industry_naics_code")
    private String industryNaicsCode;

    @Column(name = "flg_psl_qualified")
    private String pslQualified;

    @Column(name = "cod_priority_sector_category")
    private String prioritySectorCategory;

    @Column(name = "flg_default_value")
    private String defaultValue;

    @Column(name = "amt_exposure_limit")
    private Double exposureLimit;

    @Column(name = "amt_annual_sanctions_limit")
    private Double annualSanctionsLimit;
}