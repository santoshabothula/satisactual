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
@Table(name = "mst_covenant_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CovenantTypeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private CovenantTypeEmbeddedKey id;

    @Column(name = "txt_covenant_type_desc")
    private String covenantTypeDesc;

    @Column(name = "flg_default_value")
    private String defaultValue;

}