package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromoCodeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_promo")
    private String codPromo;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_promo_code_desc")
    private String promoCodeDesc;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_product")
    private String product;

	@JsonProperty("dat_promo_start")
    private LocalDate promoStart;

	@JsonProperty("dat_promo_end")
    private LocalDate promoEnd;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_promo_headline")
    private String promoHeadline;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_promo_tagline")
    private String promoTagline;

    @Size(min = 1, max = 96)
	@JsonProperty("txt_promo_offer_desc")
    private String promoOfferDesc;

    @Size(min = 1, max = 255)
	@JsonProperty("bin_promo_creative")
    private String binPromoCreative;

    @Size(min = 1, max = 255)
	@JsonProperty("bin_promo_creative_small")
    private String binPromoCreativeSmall;

	@JsonProperty("flg_customer_facing")
    private FlagYesNo customerFacing;

	@JsonProperty("num_promo_priority")
    private Integer promoPriority;
}
