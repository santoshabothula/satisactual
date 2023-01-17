package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThirdPartyTypeDTO extends BaseDTO {

	@JsonProperty("cod_third_party_type")
    private String codThirdPartyType;

	@JsonProperty("txt_third_party_type_desc")
    private String thirdPartyTypeDesc;

	@JsonProperty("flg_default_value")
    private String defaultValue;
}
