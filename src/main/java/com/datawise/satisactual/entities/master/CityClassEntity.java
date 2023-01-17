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
@Table(name = "mst_city_classes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityClassEntity extends MakerCheckerEntity {

    @EmbeddedId
    private CityClassEmbeddedKey id;

    @Column(name = "txt_city_class_desc")
    private String cityClassDesc;

    @Column(name = "amt_mthly_rent_hni")
    private Double amtMthlyRentHni;

    @Column(name = "amt_mthly_rent_affluent")
    private Double amtMthlyRentAffluent;

    @Column(name = "amt_mthly_rent_emerging")
    private Double amtMthlyRentEmerging;

    @Column(name = "amt_mthly_rent_mass")
    private Double amtMthlyRentMass;

    @Column(name = "amt_mthly_exp_per_head_hni")
    private Double amtMthlyExpPerHeadHni;

    @Column(name = "amt_mthly_exp_per_head_affluent")
    private Double amtMthlyExpPerHeadAffluent;

    @Column(name = "amt_mthly_exp_per_head_emerging")
    private Double amtMthlyExpPerHeadEmerging;

    @Column(name = "amt_mthly_exp_per_head_mass")
    private Double amtMthlyExpPerHeadMass;

}