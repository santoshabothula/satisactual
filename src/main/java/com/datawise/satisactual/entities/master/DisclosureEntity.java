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
@Table(name = "mst_disclosures")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DisclosureEntity extends MakerCheckerEntity {

    @EmbeddedId
    private DisclosureEmbeddedKey id;

    @Column(name = "txt_disclosure_desc")
    private String disclosureDesc;

}