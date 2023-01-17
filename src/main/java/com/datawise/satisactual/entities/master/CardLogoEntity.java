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
@Table(name = "mst_card_logos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardLogoEntity extends MakerCheckerEntity {

    @EmbeddedId
    private CardLogoEmbeddedKey id;

    @Column(name = "txt_logo_name")
    private String logoName;

}