package com.datawise.satisactual.entities.master;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mst_fee_lines")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeeLineEntity extends MakerCheckerEntity {

    @EmbeddedId
    private FeeLineEmbeddedKey id;

    @Column(name = "txt_fee_line_desc")
    private String feeLineDesc;

    @Column(name = "enu_fee_type")
    private String feeType;

    @Column(name = "amt_fee_charge_fixed")
    private Double feeChargeFixed;

    @Column(name = "flg_allow_input")
    private String allowInput;

    @Column(name = "enu_var_fee_base")
    private String varFeeBase;

    @Column(name = "amt_fee_charge_pct")
    private Double feeChargePct;

    @Column(name = "amt_min_var_fee_charge")
    private Double minVarFeeCharge;

    @Column(name = "amt_max_var_fee_charge")
    private Double maxVarFeeCharge;

    @Column(name = "enu_fee_frequency")
    private String feeFrequency;

    @Column(name = "enu_fee_charge_event")
    private String feeChargeEvent;

    @Column(name = "amt_high_slab_threshold")
    private Double highSlabThreshold;

    @Column(name = "rat_high_slab_offset")
    private Float ratHighSlabOffset;

    @Column(name = "amt_high_slab_fixed")
    private Double highSlabFixed;

    @Column(name = "amt_low_slab_threshold")
    private Double lowSlabThreshold;

    @Column(name = "rat_low_slab_offset")
    private Float ratLowSlabOffset;

    @Column(name = "amt_low_slab_fixed")
    private Double lowSlabFixed;

    @Column(name = "id_third_party_provider")
    private Integer idThirdPartyProvider;

    @Column(name = "flg_allow_alt_sourcing")
    private String allowAltSourcing;

    @Column(name = "cod_ins_product")
    private String insProduct;

    @Column(name = "cod_gl_account")
    private String glAccount;

    @Column(name = "flg_service_tax_applicable")
    private String serviceTaxApplicable;

    @Column(name = "flg_tax")
    private String tax;

    @Column(name = "flg_ins_premium")
    private String insPremium;

    @Column(name = "txt_print_format_conditions")
    private String printFormatConditions;

    @Column(name = "flg_default_value")
    private String defaultValue;
}