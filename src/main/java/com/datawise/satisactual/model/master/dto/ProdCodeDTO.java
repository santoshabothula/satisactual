package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdCodeDTO extends BaseDTO {

	@JsonProperty("cod_product")
    private String codProduct;

	@JsonProperty("enu_product_type")
    private String productType;

	@JsonProperty("txt_product_name")
    private String productName;

	@JsonProperty("dat_prod_offer_start")
    private LocalDate prodOfferStart;

	@JsonProperty("dat_prod_offer_end")
    private LocalDate prodOfferEnd;

	@JsonProperty("cod_currency")
    private String currency;

	@JsonProperty("cod_parent_product")
    private String parentProduct;

	@JsonProperty("flg_apply_selfserve_mode")
    private String applySelfserveMode;

	@JsonProperty("flg_e_stmt_avlbl")
    private String eStmtAvlbl;

	@JsonProperty("bin_product_brochure")
    private String binProductBrochure;

	@JsonProperty("bin_product_soc")
    private String binProductSoc;

	@JsonProperty("bin_product_mitc")
    private String binproductMitc;

	@JsonProperty("txt_mktg_tag_line")
    private String mktgTagLine;

	@JsonProperty("id_eligibility_questionnaire")
    private Long idEligibilityQuestionnaire;

	@JsonProperty("flg_default_value")
    private String defaultValue;
}
