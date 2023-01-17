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
@Table(name = "mst_livestock")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LivestockEntity extends MakerCheckerEntity {

    @EmbeddedId
    private LivestockEmbeddedKey id;

    @Column(name = "txt_livestock_desc")
    private String livestockDesc;

    @Column(name = "amt_yield_per_animal")
    private Double yieldPerAnimal;

    @Column(name = "amt_input_cost_per_animal")
    private Double inputCostPerAnimal;
}