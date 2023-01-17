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
@Table(name = "mst_int_rate")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IntRateEntity extends MakerCheckerEntity {

    @EmbeddedId
    private IntRateEmbeddedKey id;

    @Column(name = "rat_interest")
    private Double ratInterest;

}