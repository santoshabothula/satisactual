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
public class CustAttributeGroupDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_attribute_group")
    private String codAttributeGroup;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_attribute_group_desc")
    private String attributeGroupDesc;

	@JsonProperty("num_display_sequence")
    private Integer displaySequence;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;
}
