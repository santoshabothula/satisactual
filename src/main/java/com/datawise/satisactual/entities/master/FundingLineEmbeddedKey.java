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
public class FundingLineEmbeddedKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cod_funding_line")
    private String codFundingLine;

    @Column(name = "cod_funder")
    private String codFunder;

    @Column(name = "cod_rec_status")
    private String codRecordStatus;
}