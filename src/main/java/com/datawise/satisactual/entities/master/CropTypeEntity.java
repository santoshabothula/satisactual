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
@Table(name = "mst_crop_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CropTypeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private CropTypeEmbeddedKey id;

    @Column(name = "enu_crop_class")
    private String cropClass;

    @Column(name = "txt_crop_type_desc")
    private String cropTypeDesc;

    @Column(name = "num_kgs_yield_per_acre")
    private Integer kgsYieldPerAcre;

    @Column(name = "amt_purch_price_per_kg")
    private Double purchPricePerKg;

    @Column(name = "amt_input_costs_per_acre")
    private Double inputCostsPerAcre;

    @Column(name = "max_harvests_per_year")
    private Integer harvestsPerYear;

    @Column(name = "num_mths_crop_cycle")
    private Integer mthsCropCycle;

    @Column(name = "num_days_shelf_life")
    private Integer daysShelfLife;

}