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
@Table(name = "mst_promo_codes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromoCodeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private PromoCodeEmbeddedKey id;

    @Column(name = "txt_promo_code_desc")
    private String promoCodeDesc;

    @Column(name = "flg_default_value")
    private String defaultValue;

    @Column(name = "cod_product")
    private String product;

    @Column(name = "dat_promo_start")
    private LocalDate promoStart;

    @Column(name = "dat_promo_end")
    private LocalDate promoEnd;

    @Column(name = "txt_promo_headline")
    private String promoHeadline;

    @Column(name = "txt_promo_tagline")
    private String promoTagline;

    @Column(name = "txt_promo_offer_desc")
    private String promoOfferDesc;

    @Column(name = "bin_promo_creative")
    private String binPromoCreative;

    @Column(name = "bin_promo_creative_small")
    private String binPromoCreativeSmall;

    @Column(name = "flg_customer_facing")
    private String customerFacing;

    @Column(name = "num_promo_priority")
    private Integer promoPriority;

}