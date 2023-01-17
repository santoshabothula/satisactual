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
public class FeeLineDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_fee_line")
    private String codFeeLine;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_fee_line_desc")
    private String feeLineDesc;

    @Size(min = 1, max = 1)
	@JsonProperty("enu_fee_type")
    private String feeType;

	@JsonProperty("amt_fee_charge_fixed")
    private Double feeChargeFixed;

	@JsonProperty("flg_allow_input")
    private FlagYesNo allowInput;

    @Size(min = 1, max = 1)
	@JsonProperty("enu_var_fee_base")
    private String varFeeBase;

	@JsonProperty("amt_fee_charge_pct")
    private Double feeChargePct;

	@JsonProperty("amt_min_var_fee_charge")
    private Double minVarFeeCharge;

	@JsonProperty("amt_max_var_fee_charge")
    private Double maxVarFeeCharge;

    @Size(min = 1, max = 1)
	@JsonProperty("enu_fee_frequency")
    private String feeFrequency;

    @Size(min = 1, max = 1)
	@JsonProperty("enu_fee_charge_event")
    private String feeChargeEvent;

	@JsonProperty("amt_high_slab_threshold")
    private Double highSlabThreshold;

	@JsonProperty("rat_high_slab_offset")
    private Float ratHighSlabOffset;

	@JsonProperty("amt_high_slab_fixed")
    private Double highSlabFixed;

	@JsonProperty("amt_low_slab_threshold")
    private Double lowSlabThreshold;

	@JsonProperty("rat_low_slab_offset")
    private Float ratLowSlabOffset;

	@JsonProperty("amt_low_slab_fixed")
    private Double lowSlabFixed;

	@JsonProperty("id_third_party_provider")
    private Integer idThirdPartyProvider;

	@JsonProperty("flg_allow_alt_sourcing")
    private FlagYesNo allowAltSourcing;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_ins_product")
    private String insProduct;

	@JsonProperty("cod_gl_account")
    private Integer glAccount;

	@JsonProperty("flg_service_tax_applicable")
    private FlagYesNo serviceTaxApplicable;

	@JsonProperty("flg_tax")
    private FlagYesNo tax;

	@JsonProperty("flg_ins_premium")
    private FlagYesNo insPremium;

	@JsonProperty("txt_print_format_conditions")
    private String printFormatConditions;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;
}
