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
@Table(name = "mst_relations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RelationEntity extends MakerCheckerEntity {

    @EmbeddedId
    private RelationEmbeddedKey id;

    @Column(name = "txt_relation_desc")
    private String relationDesc;

    @Column(name = "flg_default_value")
    private String defaultValue;

    @Column(name = "enu_ownership_level")
    private String ownershipLevel;

    @Column(name = "flg_close_relation")
    private String closeRelation;

}