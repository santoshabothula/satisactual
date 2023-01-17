package com.datawise.satisactual.entities.master;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LanguageEmbeddedKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cod_language")
    private String codLanguage;

    @Column(name = "txt_iso_3char_code")
    private String iso3charCode;

    @Column(name = "txt_iso_2char_code")
    private String iso2charCode;

    @Column(name = "cod_rec_status")
    private String codRecordStatus;
}