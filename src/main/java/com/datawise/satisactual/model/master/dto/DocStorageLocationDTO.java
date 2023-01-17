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
public class DocStorageLocationDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_storage_location")
    private String codStorageLocation;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_storage_location_desc")
    private String storageLocationDesc;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_security_class")
    private String securityClass;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;

}
