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
public class DocTypeEmbeddedKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cod_doc_type")
    private String codDocType;

    @Column(name = "enu_doc_purpose")
    private String enuDocPurpose;

    @Column(name = "cod_rec_status")
    private String codRecordStatus;
}