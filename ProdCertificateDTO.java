package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdCertificateDTO extends BaseDTO {

	@JsonProperty("cod_certificate")
    private String codCertificate;

	@JsonProperty("txt_certificate_desc")
    private String certificateDesc;

	@JsonProperty("cod_product")
    private String product;

	@JsonProperty("enu_product_type")
    private String productType;

	@JsonProperty("id_third_party_issuer")
    private String idThirdPartyIssuer;

}
