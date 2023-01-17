package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DailyCcyRateDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_currency")
    private String codCurrency;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_base_currency")
    private String codBaseCurrency;

	@JsonProperty("dat_rate_set")
    private LocalDate rateSet;

	@JsonProperty("num_units_base_currency")
    private Integer unitsBaseCurrency;

	@JsonProperty("rat_buy")
    private Double ratBuy;

	@JsonProperty("rat_sell")
    private Double ratSell;

	@JsonProperty("rat_buy_notes")
    private Double ratBuyNotes;

	@JsonProperty("rat_sell_notes")
    private Double ratSellNotes;

	@JsonProperty("rat_buy_tt")
    private Double ratBuyTt;

	@JsonProperty("rat_sell_tt")
    private Double ratSellTt;
}
