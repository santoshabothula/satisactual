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
public class CollateralTypeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_coll_type")
    private String codCollType;

    @NotBlank
    @Size(min = 1, max = 1)
	@JsonProperty("cod_base_coll_type")
    private String baseCollType;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_coll_type_desc")
    private String collTypeDesc;

	@JsonProperty("num_max_ltv_pct")
    private Integer maxLtvPct;

	@JsonProperty("rat_depreciation")
    private Double ratDepreciation;

	@JsonProperty("num_years_active_life")
    private Integer yearsActiveLife;

    @Size(min = 1, max = 1)
	@JsonProperty("enu_depr_method")
    private String deprMethod;

	@JsonProperty("num_pct_salvage_value")
    private Double pctSalvageValue;

	@JsonProperty("flg_asset_registry_available")
    private FlagYesNo assetRegistryAvailable;

    @Size(min = 1, max = 1)
	@JsonProperty("enu_review_frequency")
    private String reviewFrequency;

	@JsonProperty("id_thirdparty_registry")
    private Long idThirdPartyRegistry;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_asset_registry_coll_type")
    private String assetRegistryCollType;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;
}
