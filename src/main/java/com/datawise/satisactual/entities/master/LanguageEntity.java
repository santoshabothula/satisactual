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
@Table(name = "mst_languages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LanguageEntity extends MakerCheckerEntity {

    @EmbeddedId
    private LanguageEmbeddedKey id;

    @Column(name = "txt_language_name")
    private String languageName;

    @Column(name = "flg_default_value")
    private String defaultValue;
}