package com.datawise.satisactual.entities.master;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntRateEmbeddedKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cod_interest_rate")
    private String codInterestRate;

    @Column(name = "dat_effective")
    private LocalDate datEffective;

    @Column(name = "cod_rec_status")
    private String codRecordStatus;
}