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
@Table(name = "mst_daily_ccy_rates")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DailyCcyRateEntity extends MakerCheckerEntity {

    @EmbeddedId
    private DailyCcyRateEmbeddedKey id;

    @Column(name = "num_units_base_currency")
    private Integer unitsBaseCurrency;

    @Column(name = "rat_buy")
    private Double ratBuy;

    @Column(name = "rat_sell")
    private Double ratSell;

    @Column(name = "rat_buy_notes")
    private Double ratBuyNotes;

    @Column(name = "rat_sell_notes")
    private Double ratSellNotes;

    @Column(name = "rat_buy_tt")
    private Double ratBuyTt;

    @Column(name = "rat_sell_tt")
    private Double ratSellTt;

}