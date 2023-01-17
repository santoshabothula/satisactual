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
@Table(name = "mst_vehicle_makes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleMakeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private VehicleMakeEmbeddedKey id;

    @Column(name = "txt_make_desc")
    private String makeDesc;

    @Column(name = "txt_manufacturer_name")
    private String manufacturerName;
}