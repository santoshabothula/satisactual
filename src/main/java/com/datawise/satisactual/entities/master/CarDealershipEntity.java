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
@Table(name = "mst_car_dealerships")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarDealershipEntity extends MakerCheckerEntity {

    @EmbeddedId
    private CarDealershipEmbeddedKey id;

    @Column(name = "cod_make_specialization")
    private String codMakeSpecialization;

    @Column(name = "flg_lon_appl_originator")
    private String lonApplOriginator;

    @Column(name = "cod_servicing_branch")
    private String servicingBranch;

}