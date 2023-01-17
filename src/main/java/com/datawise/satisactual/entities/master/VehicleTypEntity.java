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
@Table(name = "mst_vehicle_typ")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleTypEntity extends MakerCheckerEntity {

    @EmbeddedId
    private VehicleTypEmbeddedKey id;

    @Column(name = "txt_vehicle_typ_desc")
    private String vehicleTypDesc;

    @Column(name = "flg_default_value")
    private String defaultValue;

}