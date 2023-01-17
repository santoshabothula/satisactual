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
public class RelationDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_relation")
    private String codRelation;

    @Size(min = 1, max = 1)
	@JsonProperty("enu_relation_type")
    private String relationType;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_relation_desc")
    private String relationDesc;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;

    @Size(min = 1, max = 1)
	@JsonProperty("enu_ownership_level")
    private String ownershipLevel;

	@JsonProperty("flg_close_relation")
    private FlagYesNo closeRelation;
}
