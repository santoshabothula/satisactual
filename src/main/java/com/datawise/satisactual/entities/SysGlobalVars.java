package com.datawise.satisactual.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_GLOBAL_VARS")
@Getter @Setter
public class SysGlobalVars {

    @Id
    @Column(name = "txt_bank_name")
    private String txtBankName;

    @Column(name = "bin_bank_logo_file")
    private String binBankLogoFile;

    @Column(name = "txt_site_license_id")
    private String txtSiteLicenseId;

    @Column(name = "dat_system_asof")
    private String datSystemAsof;

    @Column(name = "dat_next_working_day")
    private String datNextWorkingDay;

    @Column(name = "dat_last_working_day")
    private String datLastWorkingDay;

    @Column(name = "dat_next_monthend")
    private String datNextMonthend;

    @Column(name = "dat_curr_fin_year_start")
    private String datCurrFinYearStart;

    @Column(name = "dat_curr_fin_year_end")
    private String datCurrFinYearEnd;

    @Column(name = "cod_home_ccy")
    private String codHomeCcy;

    @Column(name = "num_org_levels")
    private String numOrgLevels;

    @Column(name = "cod_home_country")
    private String codHomeCountry;

    @Column(name = "txt_bank_url")
    private String txtBankUrl;

    @Column(name = "txt_service_desk_email")
    private String txtServiceDeskEmail;

    @Column(name = "txt_service_desk_phone")
    private String txtServiceDeskPhone;

    @Column(name = "cod_language")
    private String codLanguage;

    @Column(name = "cod_time_zone")
    private String codTimeZone;

    @Column(name = "txt_business_closing_hours")
    private String txtBusinessClosingHours;

    @Column(name = "cod_css_theme")
    private String codCssTheme;

    @Column(name = "txt_home_addr_line1")
    private String txtHomeAddrLine1;

    @Column(name = "txt_home_addr_line2")
    private String txtHomeAddrLine2;

    @Column(name = "txt_home_addr_line3")
    private String txtHomeAddrLine3;

    @Column(name = "cod_home_state")
    private String codHomeState;

    @Column(name = "txt_IFSC_code")
    private String txtIFSCcode;

    @Column(name = "num_min_center_members")
    private String numMinCenterMembers;

    @Column(name = "num_max_center_members")
    private String numMaxCenterMembers;

    @Column(name = "cod_crbureau_third_party_type")
    private String codCrbureauThirdPartyType;

    @Column(name = "num_days_cpv_valid")
    private String numDaysCpvValid;

    @Column(name = "num_days_approval_valid")
    private String numDaysApprovalValid;

    @Column(name = "num_days_ecs_leadtime")
    private String numDaysEcsleadtime;

    @Column(name = "num_days_grace")
    private String numDaysGrace;

    @Column(name = "num_min_pymt_prinbal_pct")
    private String numMinPymtPrinbalPct;

    @Column(name = "num_days_appl_auto_cancel")
    private String numDaysApplAutoCancel;

    @Column(name = "num_days_passwd_expire")
    private String numDaysPasswdExpire;

    @Column(name = "enu_bureau_report_frequency")
    private String enuBureauReportFrequency;

    @Column(name = "rat_service_tax")
    private String ratServiceTax;

    @Column(name = "rat_addl_cess")
    private String ratAddlCess;

    @Column(name = "num_int_receivable_gl")
    private String numIntReceivableGl;

    @Column(name = "num_fee_receivable_gl")
    private String numFeeReceivableGl;

    @Column(name = "num_disbursal_due_gl")
    private String numDisbursalDueGl;

    @Column(name = "num_clearing_house_gl")
    private String numClearingHouseGl;

    @Column(name = "num_epay_settlement_gl")
    private String numEpaySettlementGl;

    @Column(name = "num_inter_branch_gl")
    private String numInterBranchGl;

    @Column(name = "num_cash_gl")
    private String numCashGl;

    @Column(name = "num_tax_escrow_gl")
    private String numTaxEscrowGl;

    @Column(name = "num_insprem_escrow_gl")
    private String numInspremEscrowGl;

    @Column(name = "num_service_tax_gl")
    private String numServiceTaxGl;

    @Column(name = "num_addl_cess_gl")
    private String numAddlCessGl;

    @Column(name = "num_bad_debt_provision_gl")
    private String numBadDebtProvisionGl;

    @Column(name = "num_loan_loss_reserve_gl")
    private String numLoanLossReserveGl;

    @Column(name = "num_suspense_gl")
    private String numSuspenseGl;

    @Column(name = "id_collect_campaign")
    private String idCollectCampaign;

    @Column(name = "id_collect_contact_list")
    private String idCollectContactList;

    @Column(name = "num_lon_eod_batch_num")
    private String numLonEodBatchNum;

    @Column(name = "cod_camp_design_wkflow")
    private String codCampDesignWkflow;

    @Column(name = "cod_lon_prod_design_wkflow")
    private String codLonProdDesignWkflow;

    @Column(name = "cod_fee_cust_registration")
    private String codFeeCustRegistration;

    @Column(name = "enu_cust_reg_fee_frq")
    private String enuCustRegFeeFrq;

    @Column(name = "cod_preferred_date_format")
    private String codPreferredDateFormat;

    @Column(name = "cod_rec_status")
    private String codRecStatus;

    @Column(name = "txt_last_maker_id")
    private String txtLastMakerId;

    @Column(name = "dat_last_maker")
    private String datLastMaker;

    @Column(name = "txt_last_checker_id")
    private String txtLastCheckerId;

    @Column(name = "dat_last_checker")
    private String datLastChecker;

}
