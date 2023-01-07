package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankBranchesDTO extends MakerCheckerDTO {

    @JsonProperty("cod_ifsc_bank_branch")
    private String codIfscBankBranch;

    @JsonProperty("cod_rec_status")
    private String codRecordStatus;

    @JsonProperty("txt_bank_branch_name")
    private String bankBranchName;

    @JsonProperty("cod_bank")
    private String bank;

    @JsonProperty("txt_micr_code")
    private String micrCode;

    @JsonProperty("txt_branch_address")
    private String branchAddress;

    @JsonProperty("txt_branch_contact")
    private String branchContact;

    @JsonProperty("txt_branch_city")
    private String branchCity;

    @JsonProperty("cod_branch_district")
    private String branchDistrict;

    @JsonProperty("cod_branch_state")
    private String branchState;

    @JsonProperty("cod_pin_code")
    private String pinCode;

    @JsonProperty("txt_post_office_name")
    private String postOfficeName;

    @JsonProperty("num_longitude")
    private Double longitude;

    @JsonProperty("num_latitude")
    private Double latitude;

    @JsonProperty("enu_office_type")
    private String officeType;

    @JsonProperty("dat_office_open")
    private LocalDateTime officeOpen;

    @JsonProperty("dat_office_closed")
    private LocalDateTime officeClosed;

    @JsonProperty("txt_license_number")
    private String licenseNumber;

    @JsonProperty("dat_revalidation")
    private LocalDateTime revalidation;

    @JsonProperty("flg_general_banking")
    private String generalBanking;

    @JsonProperty("flg_hsg_cons_vehicle_finance")
    private String hsgConsVehicleFinance;

    @JsonProperty("flg_corporate_banking")
    private String corporateBanking;

    @JsonProperty("flg_agri_finance")
    private String agriFinance;

    @JsonProperty("flg_specialized_msme")
    private String specializedMsme;

    @JsonProperty("flg_forex")
    private String forex;

    @JsonProperty("flg_cap_mkt_inv_banking")
    private String capMktInvBanking;

    @JsonProperty("flg_govt_business")
    private String govtBusiness;

    @JsonProperty("flg_taxes")
    private String taxes;

    @JsonProperty("flg_ppf_pension_services")
    private String ppfPensionServices;

    @JsonProperty("flg_cust_self_service")
    private String custSelfService;

    @JsonProperty("flg_ultra_small")
    private String ultraSmall;

    @JsonProperty("flg_treasury_branch")
    private String treasuryBranch;

    @JsonProperty("flg_forex_treasury")
    private String forexTreasury;

    @JsonProperty("flg_currency_chest")
    private String currencyChest;

    @JsonProperty("flg_small_coin_depot")
    private String smallCoinDepot;

    @JsonProperty("flg_asset_recovery_branch")
    private String assetRecoveryBranch;

    @JsonProperty("flg_clearing_payment_svc")
    private String clearingPaymentSvc;

    @JsonProperty("flg_deposit_processing_center")
    private String depositProcessingCenter;

    @JsonProperty("flg_loan_processing_center")
    private String loanProcessingCenter;

    @JsonProperty("flg_forex_processing_center")
    private String forexProcessingCenter;

    @JsonProperty("flg_trade_fin_processing_center")
    private String tradeFinProcessingCenter;

    @JsonProperty("flg_administrative_office")
    private String administrativeOffice;

    @JsonProperty("flg_extension_counter")
    private String extensionCounter;

    @JsonProperty("flg_satellite_office")
    private String satelliteOffice;

    @JsonProperty("flg_mobile_office")
    private String mobileOffice;

    @JsonProperty("flg_service_branch")
    private String serviceBranch;

    @JsonProperty("flg_mobile_atm")
    private String mobileAtm;

    @JsonProperty("flg_onsite_atm")
    private String onsiteAtm;

    @JsonProperty("flg_offsite_atm")
    private String offsiteAtm;

    @JsonProperty("flg_rep_office")
    private String repOffice;

    @JsonProperty("flg_exchg_bureau")
    private String exchangeBureau;

    @JsonProperty("enu_auth_forex_dealer_category")
    private String authForexDealerCategory;

    @JsonProperty("dat_forex_authorized")
    private LocalDateTime forexAuthorized;
}
