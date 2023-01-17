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
@Table(name = "mst_major_cities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MajorCityEntity extends MakerCheckerEntity {

    @EmbeddedId
    private MajorCityEmbeddedKey id;

    @Column(name = "cod_state_code")
    private String stateCode;

    @Column(name = "txt_city_name")
    private String cityName;

    @Column(name = "num_longitude_nw")
    private Double longitudeNw;

    @Column(name = "num_latitude_nw")
    private Double latitudeNw;

    @Column(name = "num_longitude_se")
    private Double longitudeSe;

    @Column(name = "num_latitude_se")
    private Double latitudeSe;

    @Column(name = "cod_time_zone")
    private String timeZone;

    @Column(name = "num_hours_gmt_offset")
    private Double hoursGmtOffset;

    @Column(name = "flg_daylight_savings")
    private String daylightSavings;

    @Column(name = "cod_city_class")
    private String cityClass;
}