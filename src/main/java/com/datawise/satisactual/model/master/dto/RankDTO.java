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
public class RankDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_rank")
    private String codRank;

    @NotBlank
    @Size(min = 1, max = 10)
	@JsonProperty("enu_wing")
    private String wing;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_rank_desc")
    private String rankDesc;

	@JsonProperty("num_seniority")
    private Integer seniority;

	@JsonProperty("flg_officer")
    private FlagYesNo officer;
}
