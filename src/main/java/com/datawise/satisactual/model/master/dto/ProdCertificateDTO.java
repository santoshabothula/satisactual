package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdCertificateDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_certificate")
    private String codCertificate;

    @Size(min = 1, max = 96)
	@JsonProperty("txt_certificate_desc")
    private String certificateDesc;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_product")
    private String product;

    @Size(min = 1, max = 7)
	@JsonProperty("enu_product_type")
    private String productType;

	@JsonProperty("id_third_party_issuer")
    private Long idThirdPartyIssuer;
}
