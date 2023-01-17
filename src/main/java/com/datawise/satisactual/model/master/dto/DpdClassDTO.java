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
public class DpdClassDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_dpd_class")
    private String codDpdClass;

    @NotBlank
	@JsonProperty("flg_rewrite")
    private FlagYesNo isRewrite;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_dpd_class_desc")
    private String dpdClassDesc;

	@JsonProperty("num_min_days_past_due")
    private Integer minDaysPastDue;

	@JsonProperty("num_max_days_past_due")
    private Integer maxDaysPastDue;

	@JsonProperty("rat_pct_provision")
    private Double ratPctProvision;

	@JsonProperty("rat_pct_provision_secured")
    private Double ratPctProvisionSecured;

	@JsonProperty("rat_pct_provision_commercial")
    private Double ratPctProvisionCommercial;

	@JsonProperty("rat_pct_provision_group")
    private Double ratPctProvisionGroup;

}
