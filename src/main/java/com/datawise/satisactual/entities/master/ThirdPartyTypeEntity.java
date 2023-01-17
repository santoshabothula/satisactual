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
@Table(name = "mst_third_party_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThirdPartyTypeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private ThirdPartyTypeEmbeddedKey id;

    @Column(name = "txt_third_party_type_desc")
    private String thirdPartyTypeDesc;

    @Column(name = "flg_default_value")
    private String defaultValue;
}