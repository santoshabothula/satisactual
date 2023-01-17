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
public class RegdEmployerEmbeddedKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "txt_employer_shortname")
    private String employerShortname;

    @Column(name = "id_third_party_employer")
    private Long idThirdPartyEmployer;

    @Column(name = "cod_rec_status")
    private String codRecordStatus;
}