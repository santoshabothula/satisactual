package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankCodeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 24)
    @JsonProperty("cod_bank")
    private String codBank;

    @Size(min = 1, max = 24)
    @JsonProperty("txt_bank_short_name")
    private String bankShortName;

    @Size(min = 1, max = 24)
    @JsonProperty("txt_bank_name")
    private String bankName;

    @Size(min = 1, max = 4)
    @JsonProperty("cod_bank_type")
    private String bankType;

    @Size(min = 1, max = 4)
    @JsonProperty("cod_owner_bank")
    private String ownerBank;

    @Size(min = 1, max = 4)
    @JsonProperty("cod_owner_country")
    private String ownerCountry;

    @JsonProperty("dat_opened")
    private LocalDate opened;

    @JsonProperty("dat_closed_or_merged")
    private LocalDate closedOrMerged;

    @Size(min = 1, max = 4)
    @JsonProperty("cod_bank_merged_with")
    private String bankMergedWith;

    @JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;
}
