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
@Table(name = "mst_collateral_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CollateralTypeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private CollateralTypeEmbeddedKey id;

    @Column(name = "cod_base_coll_type")
    private String baseCollType;

    @Column(name = "txt_coll_type_desc")
    private String collTypeDesc;

    @Column(name = "num_max_ltv_pct")
    private Integer maxLtvPct;

    @Column(name = "rat_depreciation")
    private Double ratDepreciation;

    @Column(name = "num_years_active_life")
    private Integer yearsActiveLife;

    @Column(name = "enu_depr_method")
    private String deprMethod;

    @Column(name = "num_pct_salvage_value")
    private Double pctSalvageValue;

    @Column(name = "flg_asset_registry_available")
    private String assetRegistryAvailable;

    @Column(name = "enu_review_frequency")
    private String reviewFrequency;

    @Column(name = "id_thirdparty_registry")
    private Long idThirdPartyRegistry;

    @Column(name = "txt_asset_registry_coll_type")
    private String assetRegistryCollType;

    @Column(name = "flg_default_value")
    private String defaultValue;
}