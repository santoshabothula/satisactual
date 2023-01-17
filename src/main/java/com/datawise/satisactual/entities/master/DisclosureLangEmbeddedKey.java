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
public class DisclosureLangEmbeddedKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cod_disclosure")
    private String codDisclosure;

    @Column(name = "cod_language")
    private String codLanguage;

    @Column(name = "cod_rec_status")
    private String codRecordStatus;
}