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
@Table(name = "mst_currency")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyEntity extends MakerCheckerEntity {

    @EmbeddedId
    private CurrencyEmbeddedKey id;

    @Column(name = "txt_currency_desc")
    private String currencyDesc;

    @Column(name = "txt_currency_symbol")
    private String currencySymbol;

    @Column(name = "cod_country")
    private String country;

    @Column(name = "num_decimal_places")
    private Integer decimalPlaces;

    @Column(name = "txt_minor_unit_name")
    private String minorUnitName;

    @Column(name = "flg_default_value")
    private String defaultValue;

}