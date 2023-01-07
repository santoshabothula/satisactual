package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
public class AddressTypeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
    @JsonProperty("cod_addr_type")
    private String codAddressType;

    @NotBlank
    @Size(min = 1, max = 24)
    @JsonProperty("txt_addr_type_desc")
    private String addressTypeDesc;

    @JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;

    @JsonProperty("flg_accept_as_primary")
    private FlagYesNo acceptAsPrimary;

    @JsonProperty("flg_accept_for_individual")
    private FlagYesNo acceptForIndividual;

    @JsonProperty("flg_accept_for_organization")
    private FlagYesNo acceptForOrganization;
}
