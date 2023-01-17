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
public class DailyCcyRateEmbeddedKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cod_currency")
    private String codCurrency;

    @Column(name = "cod_base_currency")
    private String codBaseCurrency;

    @Column(name = "dat_rate_set")
    private LocalDate rateSet;

    @Column(name = "cod_rec_status")
    private String codRecordStatus;
}