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
@Table(name = "mst_funders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FunderEntity extends MakerCheckerEntity {

    @EmbeddedId
    private FunderEmbeddedKey id;

    @Column(name = "txt_funder_desc")
    private String funderDesc;

    @Column(name = "flg_default_value")
    private String defaultValue;

}