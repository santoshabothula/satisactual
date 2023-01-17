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
@Table(name = "mst_disclosure_lang")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DisclosureLangEntity extends MakerCheckerEntity {

    @EmbeddedId
    private DisclosureLangEmbeddedKey id;

    @Column(name = "txt_disclosure_phrasing")
    private String disclosurePhrasing;

}