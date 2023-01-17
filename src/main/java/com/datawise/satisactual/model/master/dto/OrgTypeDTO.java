package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrgTypeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_organization_type")
    private String codOrganizationType;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_organization_type_desc")
    private String organizationTypeDesc;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;
}
