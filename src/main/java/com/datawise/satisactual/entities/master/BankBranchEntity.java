package com.datawise.satisactual.entities.master;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "mst_bank_branches")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankBranchEntity extends MakerCheckerEntity {

    @EmbeddedId
    private BankBranchEmbeddedKey id;

    @Column(name = "txt_bank_branch_name")
    private String bankBranchName;

    @Column(name = "cod_bank")
    private String bank;

    @Column(name = "txt_micr_code")
    private String micrCode;

    @Column(name = "txt_branch_address")
    private String branchAddress;

    @Column(name = "txt_branch_contact")
    private String branchContact;

    @Column(name = "txt_branch_city")
    private String branchCity;

    @Column(name = "cod_branch_district")
    private String branchDistrict;

    @Column(name = "cod_branch_state")
    private String branchState;

    @Column(name = "cod_pin_code")
    private String pinCode;

    @Column(name = "txt_post_office_name")
    private String postOfficeName;

    @Column(name = "num_longitude")
    private Double longitude;

    @Column(name = "num_latitude")
    private Double latitude;

    @Column(name = "enu_office_type")
    private String officeType;

    @Column(name = "dat_office_open")
    private LocalDateTime officeOpen;

    @Column(name = "dat_office_closed")
    private LocalDateTime officeClosed;

    @Column(name = "txt_license_number")
    private String licenseNumber;

    @Column(name = "dat_revalidation")
    private LocalDateTime revalidation;

    @Column(name = "flg_general_banking")
    private String generalBanking;

    @Column(name = "flg_hsg_cons_vehicle_finance")
    private String hsgConsVehicleFinance;

    @Column(name = "flg_corporate_banking")
    private String corporateBanking;

    @Column(name = "flg_agri_finance")
    private String agriFinance;

    @Column(name = "flg_specialized_msme")
    private String specializedMsme;

    @Column(name = "flg_forex")
    private String forex;

    @Column(name = "flg_cap_mkt_inv_banking")
    private String capMktInvBanking;

    @Column(name = "flg_govt_business")
    private String govtBusiness;

    @Column(name = "flg_taxes")
    private String taxes;

    @Column(name = "flg_ppf_pension_services")
    private String ppfPensionServices;

    @Column(name = "flg_cust_self_service")
    private String custSelfService;

    @Column(name = "flg_ultra_small")
    private String ultraSmall;

    @Column(name = "flg_treasury_branch")
    private String treasuryBranch;

    @Column(name = "flg_forex_treasury")
    private String forexTreasury;

    @Column(name = "flg_currency_chest")
    private String currencyChest;

    @Column(name = "flg_small_coin_depot")
    private String smallCoinDepot;

    @Column(name = "flg_asset_recovery_branch")
    private String assetRecoveryBranch;

    @Column(name = "flg_clearing_payment_svc")
    private String clearingPaymentSvc;

    @Column(name = "flg_deposit_processing_center")
    private String depositProcessingCenter;

    @Column(name = "flg_loan_processing_center")
    private String loanProcessingCenter;

    @Column(name = "flg_forex_processing_center")
    private String forexProcessingCenter;

    @Column(name = "flg_trade_fin_processing_center")
    private String tradeFinProcessingCenter;

    @Column(name = "flg_administrative_office")
    private String administrativeOffice;

    @Column(name = "flg_extension_counter")
    private String extensionCounter;

    @Column(name = "flg_satellite_office")
    private String satelliteOffice;

    @Column(name = "flg_mobile_office")
    private String mobileOffice;

    @Column(name = "flg_service_branch")
    private String serviceBranch;

    @Column(name = "flg_mobile_atm")
    private String mobileAtm;

    @Column(name = "flg_onsite_atm")
    private String onsiteAtm;

    @Column(name = "flg_offsite_atm")
    private String offsiteAtm;

    @Column(name = "flg_rep_office")
    private String repOffice;

    @Column(name = "flg_exchg_bureau")
    private String exchangeBureau;

    @Column(name = "enu_auth_forex_dealer_category")
    private String authForexDealerCategory;

    @Column(name = "dat_forex_authorized")
    private LocalDateTime forexAuthorized;
}
