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
@Table(name = "mst_fin_ratios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinRatioEntity extends MakerCheckerEntity {

    @EmbeddedId
    private FinRatioEmbeddedKey id;

    @Column(name = "txt_ratio_name")
    private String ratioName;

    @Column(name = "flg_denom_prev_period")
    private String denomPrevPeriod;
}