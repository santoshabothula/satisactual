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
@Table(name = "mst_user_designation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDesignationEntity extends MakerCheckerEntity {

    @EmbeddedId
    private UserDesignationEmbeddedKey id;

    @Column(name = "txt_designation_desc")
    private String designationDesc;

    @Column(name = "cod_parent_designation")
    private String parentDesignation;

    @Column(name = "flg_default_value")
    private String defaultValue;
}