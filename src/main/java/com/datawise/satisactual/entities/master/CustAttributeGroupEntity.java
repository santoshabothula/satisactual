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
@Table(name = "mst_cust_attribute_groups")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustAttributeGroupEntity extends MakerCheckerEntity {

    @EmbeddedId
    private CustAttributeGroupEmbeddedKey id;

    @Column(name = "num_display_sequence")
    private Integer displaySequence;

    @Column(name = "txt_attribute_group_desc")
    private String attributeGroupDesc;

    @Column(name = "flg_default_value")
    private String defaultValue;

}