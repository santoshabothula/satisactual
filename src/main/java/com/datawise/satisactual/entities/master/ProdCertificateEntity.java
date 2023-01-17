package com.datawise.satisactual.entities.master;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mst_prod_certificates")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdCertificateEntity extends MakerCheckerEntity {

    @EmbeddedId
    private ProdCertificateEmbeddedKey id;

    @Column(name = "txt_certificate_desc")
    private String certificateDesc;

    @Column(name = "cod_product")
    private String product;

    @Column(name = "enu_product_type")
    private String productType;

    @Column(name = "id_third_party_issuer")
    private Long idThirdPartyIssuer;
}