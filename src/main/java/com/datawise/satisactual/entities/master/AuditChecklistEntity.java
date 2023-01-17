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
@Table(name = "mst_audit_checklist")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditChecklistEntity extends MakerCheckerEntity {

    @EmbeddedId
    private AuditChecklistEmbeddedKey id;

    @Column(name = "txt_item_desc")
    private String itemDesc;


    @Column(name = "num_display_sequence")
    private Integer displaySequence;


    @Column(name = "flg_group_loan_check")
    private String groupLoanCheck;


    @Column(name = "flg_commercial_loan_check")
    private String commercialLoanCheck;


    @Column(name = "flg_secured_loan_check")
    private String securedLoanCheck;


    @Column(name = "flg_guarantor_required_check")
    private String guarantorRequiredCheck;


    @Column(name = "flg_fixed_rate_check")
    private String fixedRateCheck;


    @Column(name = "flg_multi_disburse_check")
    private String multiDisburseCheck;


    @Column(name = "flg_debt_consolidation_check")
    private String debtConsolidationCheck;


    @Column(name = "flg_rate_discount_check")
    private String rateDiscountCheck;


    @Column(name = "flg_early_payoff_allowed_check")
    private String earlyPayoffAllowedCheck;


    @Column(name = "flg_interest_subvention_check")
    private String interestSubventionCheck;


    @Column(name = "flg_priority_sector_check")
    private String prioritySectorCheck;


    @Column(name = "flg_default_value")
    private String defaultValue;
}
