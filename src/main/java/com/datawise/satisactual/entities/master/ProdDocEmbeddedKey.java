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
public class ProdDocEmbeddedKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cod_product")
    private String codProduct;

    @Column(name = "cod_doc_type")
    private String codDocType;

    @Column(name = "enu_doc_purpose")
    private String docPurpose;

    @Column(name = "cod_rec_status")
    private String codRecordStatus;
}