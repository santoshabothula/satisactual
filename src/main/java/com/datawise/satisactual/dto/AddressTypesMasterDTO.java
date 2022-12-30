package com.datawise.satisactual.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AddressTypesMasterDTO extends MakerCheckerDTO {

    @NotBlank
    @Size(min = 1, max = 4)
    @JsonProperty("cod_addr_type")
    private String codAddressType;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("cod_rec_status")
    private String codRecordStatus;

    @NotBlank
    @Size(min = 1, max = 24)
    @JsonProperty("txt_addr_type_desc")
    private String addressTypeDesc;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("flg_default_value")
    private String defaultValue;

    @Size(min = 1, max = 1)
    @JsonProperty("flg_accept_as_primary")
    private String acceptAsPrimary;

    @Size(min = 1, max = 1)
    @JsonProperty("flg_accept_for_individual")
    private String acceptForIndividual;

    @Size(min = 1, max = 1)
    @JsonProperty("flg_accept_for_organization")
    private String acceptForOrganization;
}
