package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.enums.FlagYesNo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlockCodeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 12)
    @Column(name = "cod_block")
    private String codBlock;

    @Size(min = 1, max = 12)
    @Column(name = "cod_district")
    private String district;

    @Size(min = 1, max = 12)
    @Column(name = "cod_state")
    private String state;

    @Size(min = 1, max = 4)
    @Column(name = "cod_country")
    private String country;

    @Size(min = 1, max = 24)
    @Column(name = "txt_block_name")
    private String blockName;

    @Column(name = "flg_default_value")
    private FlagYesNo defaultValue;
}
