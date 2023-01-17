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
public class CustomModelAttribEmbeddedKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cod_scoring_model")
    private String codScoringModel;

    @Column(name = "cod_attribute")
    private String codAttribute;

    @Column(name = "txt_attrib_value_min")
    private String attribValueMin;

    @Column(name = "txt_attrib_value_max")
    private String attribValueMax;

    @Column(name = "cod_rec_status")
    private String codRecordStatus;
}