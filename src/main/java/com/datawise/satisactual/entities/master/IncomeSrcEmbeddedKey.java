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
public class IncomeSrcEmbeddedKey implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "cod_income_src")
    private String codIncomeSrc;

    @Column(name = "cod_rec_status")
    private String codRecordStatus;
}