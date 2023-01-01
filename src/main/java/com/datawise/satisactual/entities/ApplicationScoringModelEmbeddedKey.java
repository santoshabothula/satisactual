package com.datawise.satisactual.entities;

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
public class ApplicationScoringModelEmbeddedKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cod_scoring_model")
    private String codScoringModel;

    @Column(name = "dat_valid_from")
    private LocalDate validFrom;

    @Column(name = "cod_rec_status")
    private String codRecordStatus;
}
