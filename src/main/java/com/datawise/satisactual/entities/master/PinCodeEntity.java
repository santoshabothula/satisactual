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
@Table(name = "mst_pin_codes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PinCodeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private PinCodeEmbeddedKey id;

    @Column(name = "cod_state_code")
    private String stateCode;

    @Column(name = "txt_district_name")
    private String districtName;

    @Column(name = "txt_city_name")
    private String cityName;

    @Column(name = "txt_post_office_name")
    private String postOfficeName;

    @Column(name = "num_longitude")
    private Double longitude;

    @Column(name = "num_latitude")
    private Double latitude;

    @Column(name = "flg_default_value")
    private String defaultValue;

}