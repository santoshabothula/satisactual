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
public class PinCodeEmbeddedKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cod_pin_code")
    private String codPinCode;

    @Column(name = "cod_country")
    private String codCountry;

    @Column(name = "cod_district")
    private String codDistrict;

    @Column(name = "cod_rec_status")
    private String codRecordStatus;
}