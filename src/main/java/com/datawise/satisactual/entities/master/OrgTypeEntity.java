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
@Table(name = "mst_org_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrgTypeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private OrgTypeEmbeddedKey id;

    @Column(name = "txt_organization_type_desc")
    private String organizationTypeDesc;

    @Column(name = "flg_default_value")
    private String defaultValue;
}