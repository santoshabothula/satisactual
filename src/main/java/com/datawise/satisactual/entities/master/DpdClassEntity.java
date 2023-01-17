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
@Table(name = "mst_dpd_classes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DpdClassEntity extends MakerCheckerEntity {

    @EmbeddedId
    private DpdClassEmbeddedKey id;

    @Column(name = "txt_dpd_class_desc")
    private String dpdClassDesc;

    @Column(name = "num_min_days_past_due")
    private Integer minDaysPastDue;

    @Column(name = "num_max_days_past_due")
    private Integer maxDaysPastDue;

    @Column(name = "rat_pct_provision")
    private Double ratPctProvision;

    @Column(name = "rat_pct_provision_secured")
    private Double ratPctProvisionSecured;

    @Column(name = "rat_pct_provision_commercial")
    private Double ratPctProvisionCommercial;

    @Column(name = "rat_pct_provision_group")
    private Double ratPctProvisionGroup;

}