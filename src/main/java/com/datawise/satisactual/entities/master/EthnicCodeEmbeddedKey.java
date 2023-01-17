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
public class EthnicCodeEmbeddedKey implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "cod_ethnicity")
    private String codEthnicity;

    @Column(name = "cod_rec_status")
    private String codRecordStatus;
}