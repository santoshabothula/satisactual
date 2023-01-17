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
@Table(name = "mst_cust_attributes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustAttributeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private CustAttributeEmbeddedKey id;

    @Column(name = "txt_attribute_desc")
    private String attributeDesc;

    @Column(name = "enu_attrib_type")
    private String attribType;

    @Column(name = "txt_list_values")
    private String listValues;

    @Column(name = "enu_attrib_source")
    private String attribSource;

    @Column(name = "cod_attribute_group")
    private String attributeGroup;

    @Column(name = "flg_apply_to_individual")
    private String applyToIndividual;

    @Column(name = "flg_apply_to_organisation")
    private String applyToOrganisation;

}