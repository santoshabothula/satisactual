package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankTypeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
    @JsonProperty("cod_bank_type")
    private String codBankType;

    @Size(min = 1, max = 24)
    @JsonProperty("txt_bank_type_desc")
    private String bankTypeDesc;

    @JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;
}
