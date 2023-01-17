package com.datawise.satisactual.entities.master;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "mst_prod_codes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdCodeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private ProdCodeEmbeddedKey id;
    
    @Column(name = "enu_product_type")
    private String productType;

    @Column(name = "txt_product_name")
    private String productName;

    @Column(name = "dat_prod_offer_start")
    private LocalDate prodOfferStart;

    @Column(name = "dat_prod_offer_end")
    private LocalDate prodOfferEnd;

    @Column(name = "cod_currency")
    private String currency;

    @Column(name = "cod_parent_product")
    private String parentProduct;

    @Column(name = "flg_apply_selfserve_mode")
    private String applySelfserveMode;

    @Column(name = "flg_e_stmt_avlbl")
    private String eStmtAvlbl;

    @Column(name = "bin_product_brochure")
    private String binProductBrochure;

    @Column(name = "bin_product_soc")
    private String binProductSoc;

    @Column(name = "bin_product_mitc")
    private String binProductMitc;

    @Column(name = "txt_mktg_tag_line")
    private String mktgTagLine;

    @Column(name = "id_eligibility_questionnaire")
    private Long idEligibilityQuestionnaire;

    @Column(name = "flg_default_value")
    private String defaultValue;

}