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
public class ProdCodeDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_product")
    private String codProduct;

	@JsonProperty("enu_product_type")
    private String productType;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_product_name")
    private String productName;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
	@JsonProperty("dat_prod_offer_start")
    private LocalDate prodOfferStart;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
	@JsonProperty("dat_prod_offer_end")
    private LocalDate prodOfferEnd;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_currency")
    private String currency;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_parent_product")
    private String parentProduct;

	@JsonProperty("flg_apply_selfserve_mode")
    private FlagYesNo applySelfserveMode;

	@JsonProperty("flg_e_stmt_avlbl")
    private FlagYesNo eStmtAvlbl;

    @Size(min = 1, max = 255)
	@JsonProperty("bin_product_brochure")
    private String binProductBrochure;

    @Size(min = 1, max = 255)
	@JsonProperty("bin_product_soc")
    private String binProductSoc;

    @Size(min = 1, max = 255)
	@JsonProperty("bin_product_mitc")
    private String binproductMitc;

    @Size(min = 1, max = 255)
	@JsonProperty("txt_mktg_tag_line")
    private String mktgTagLine;

	@JsonProperty("id_eligibility_questionnaire")
    private Long idEligibilityQuestionnaire;

	@JsonProperty("flg_default_value")
    private String defaultValue;
}
