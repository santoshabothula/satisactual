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
@Table(name = "mst_country_codes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryCodeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private CountryCodeEmbeddedKey id;

    @Column(name = "txt_country_name")
    private String countryName;

    @Column(name = "txt_nationality_name")
    private String nationalityName;

    @Column(name = "cod_country_alternative")
    private String countryAlternative;

    @Column(name = "txt_phone_code")
    private String phoneCode;

    @Column(name = "num_hours_gmt_offset")
    private Double hoursGmtOffset;

    @Column(name = "flg_default_value")
    private String defaultValue;
}