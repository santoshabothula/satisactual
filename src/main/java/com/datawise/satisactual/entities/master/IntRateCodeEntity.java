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
@Table(name = "mst_int_rate_codes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IntRateCodeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private IntRateCodeEmbeddedKey id;

    @Column(name = "txt_rate_desc")
    private String rateDesc;

    @Column(name = "cod_base_rate")
    private String baseRate;

    @Column(name = "rat_interest_offset")
    private Double ratInterestOffset;
}