package com.datawise.satisactual.entities.master;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "mst_vehicle_models")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleModelEntity extends MakerCheckerEntity {

    @EmbeddedId
    private VehicleModelEmbeddedKey id;

    @Column(name = "txt_model_desc")
    private String modelDesc;

    @Column(name = "amt_onroad_cost")
    private Double onRoadCost;

    @Column(name = "dat_launched")
    private LocalDate launched;
}