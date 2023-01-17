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
@Table(name = "mst_time_zones")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeZoneEntity extends MakerCheckerEntity {

    @EmbeddedId
    private TimeZoneEmbeddedKey id;

    @Column(name = "txt_time_zone_name")
    private String timeZoneName;

    @Column(name = "num_hours_gmt_offset")
    private Double hoursGmtOffset;

    @Column(name = "flg_daylight_savings")
    private String daylightSavings;

    @Column(name = "flg_default_value")
    private String defaultValue;
}