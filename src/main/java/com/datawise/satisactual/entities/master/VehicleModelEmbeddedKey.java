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
public class VehicleModelEmbeddedKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cod_model")
    private String codModel;

    @Column(name = "cod_make")
    private String codMake;

    @Column(name = "cod_rec_status")
    private String codRecordStatus;
}