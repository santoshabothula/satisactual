package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDTO extends MakerCheckerDTO {

    @NotBlank
    @Size(min = 1, max = 4)
    @JsonProperty("cod_currency")
    private String codCurrency;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("cod_rec_status")
    private String codRecordStatus;

    @NotBlank
    @Size(min = 1, max = 24)
    @JsonProperty("txt_currency_desc")
    private String txt_currency_desc;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("txt_currency_symbol")
    private String currencySymbol;

    @Size(min = 1, max = 4)
    @JsonProperty("cod_country")
    private String country;

    @JsonProperty("num_decimal_places")
    private Integer decimalPlaces;

    @Size(min = 1, max = 48)
    @JsonProperty("txt_minor_unit_name")
    private String minorUnitName;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("flg_default_value")
    private String idDefaultValue;
}
