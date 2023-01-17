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
@Table(name = "mst_block_codes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlockCodeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private BlockCodeEmbeddedKey id;

    @Column(name = "cod_district")
    private String district;

    @Column(name = "cod_state")
    private String state;

    @Column(name = "cod_country")
    private String country;

    @Column(name = "txt_block_name")
    private String blockName;

    @Column(name = "flg_default_value")
    private String defaultValue;
}
