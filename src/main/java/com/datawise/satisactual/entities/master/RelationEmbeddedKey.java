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
public class RelationEmbeddedKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cod_relation")
    private String codRelation;

    @Column(name = "enu_relation_type")
    private String relationType;

    @Column(name = "cod_rec_status")
    private String codRecordStatus;
}