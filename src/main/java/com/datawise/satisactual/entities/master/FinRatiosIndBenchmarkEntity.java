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
@Table(name = "mst_fin_ratios_ind_benchmark")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinRatiosIndBenchmarkEntity extends MakerCheckerEntity {

    @EmbeddedId
    private FinRatiosIndBenchmarkEmbeddedKey id;

    @Column(name = "num_value_min")
    private Float valueMin;

    @Column(name = "num_value_max")
    private Float valueMax;
}