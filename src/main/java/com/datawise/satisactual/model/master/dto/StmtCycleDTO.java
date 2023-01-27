package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StmtCycleDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_stmt_cycle")
    private String codStmtCycle;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_stmt_cycle_desc")
    private String stmtCycleDesc;

	@JsonProperty("num_day_of_mth")
    private Integer dayOfMth;

	@JsonProperty("num_days_stmt_lead_time")
    private Integer daysStmtLeadTime;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
	@JsonProperty("dat_last_cycle_processed")
    private LocalDate lastCycleProcessed;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;
}
