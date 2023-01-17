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
@Table(name = "mst_prefixes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrefixEntity extends MakerCheckerEntity {

    @EmbeddedId
    private PrefixEmbeddedKey id;

    @Column(name = "txt_prefix_desc")
    private String prefixDesc;

    @Column(name = "flg_default_value")
    private String defaultValue;

}