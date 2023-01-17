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
public class ThirdPartyEmbeddedKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id_third_party")
    private Long idThirdParty;

    @Column(name = "cod_rec_status")
    private String codRecordStatus;
}