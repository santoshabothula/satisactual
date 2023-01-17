package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LanguageDTO extends BaseDTO {

    @NotNull
    @Size(min = 1, max = 4)
	@JsonProperty("cod_language")
    private String codLanguage;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_language_name")
    private String languageName;

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("txt_iso_3char_code")
    private String iso3charCode;

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("txt_iso_2char_code")
    private String iso2charCode;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;
}
