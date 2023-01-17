package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CusFinStmtLineDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_fin_stmt_line")
    private String codFinStmtLine;

    @NotBlank
    @Size(min = 1, max = 48)
	@JsonProperty("txt_fin_stmt_line_name")
    private String finStmtLineName;

    @Size(min = 1, max = 1)
	@JsonProperty("enu_line_type")
    private String lineType;

	@JsonProperty("num_line_seq")
    private Integer lineSeq;
}
