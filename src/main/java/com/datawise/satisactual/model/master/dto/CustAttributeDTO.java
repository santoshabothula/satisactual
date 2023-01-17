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
public class CustAttributeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_attribute")
    private String codAttribute;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_attribute_desc")
    private String attributeDesc;

    @Size(min = 1, max = 1)
	@JsonProperty("enu_attrib_type")
    private String attribType;

    @Size(min = 1, max = 255)
	@JsonProperty("txt_list_values")
    private String listValues;

    @Size(min = 1, max = 1)
	@JsonProperty("enu_attrib_source")
    private String attribSource;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_attribute_group")
    private String attributeGroup;

	@JsonProperty("flg_apply_to_individual")
    private FlagYesNo applyToIndividual;

	@JsonProperty("flg_apply_to_organisation")
    private FlagYesNo applyToOrganisation;
}
