package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankBranchDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 24)
    @JsonProperty("cod_ifsc_bank_branch")
    private String codIfscBankBranch;

    @Size(min = 1, max = 24)
    @JsonProperty("txt_bank_branch_name")
    private String bankBranchName;

    @Size(min = 1, max = 24)
    @JsonProperty("cod_bank")
    private String bank;

    @Size(min = 1, max = 24)
    @JsonProperty("txt_micr_code")
    private String micrCode;

    @Size(min = 1, max = 24)
    @JsonProperty("txt_branch_address")
    private String branchAddress;

    @Size(min = 1, max = 24)
    @JsonProperty("txt_branch_contact")
    private String branchContact;

    @Size(min = 1, max = 24)
    @JsonProperty("txt_branch_city")
    private String branchCity;

    @Size(min = 1, max = 12)
    @JsonProperty("cod_branch_district")
    private String branchDistrict;

    @Size(min = 1, max = 12)
    @JsonProperty("cod_branch_state")
    private String branchState;

    @Size(min = 1, max = 8)
    @JsonProperty("cod_pin_code")
    private String pinCode;

    @Size(min = 1, max = 24)
    @JsonProperty("txt_post_office_name")
    private String postOfficeName;

    @JsonProperty("num_longitude")
    private Double longitude;

    @JsonProperty("num_latitude")
    private Double latitude;

    @Size(min = 1, max = 1)
    @JsonProperty("enu_office_type")
    private String officeType;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonProperty("dat_office_open")
    private LocalDateTime officeOpen;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonProperty("dat_office_closed")
    private LocalDateTime officeClosed;

    @Size(min = 1, max = 24)
    @JsonProperty("txt_license_number")
    private String licenseNumber;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonProperty("dat_revalidation")
    private LocalDateTime revalidation;

    @JsonProperty("flg_general_banking")
    private FlagYesNo generalBanking;

    @JsonProperty("flg_hsg_cons_vehicle_finance")
    private FlagYesNo hsgConsVehicleFinance;

    @JsonProperty("flg_corporate_banking")
    private FlagYesNo corporateBanking;

    @JsonProperty("flg_agri_finance")
    private FlagYesNo agriFinance;

    @JsonProperty("flg_specialized_msme")
    private FlagYesNo specializedMsme;

    @JsonProperty("flg_forex")
    private FlagYesNo forex;

    @JsonProperty("flg_cap_mkt_inv_banking")
    private FlagYesNo capMktInvBanking;

    @JsonProperty("flg_govt_business")
    private FlagYesNo govtBusiness;

    @JsonProperty("flg_taxes")
    private FlagYesNo taxes;

    @JsonProperty("flg_ppf_pension_services")
    private FlagYesNo ppfPensionServices;

    @JsonProperty("flg_cust_self_service")
    private FlagYesNo custSelfService;

    @JsonProperty("flg_ultra_small")
    private FlagYesNo ultraSmall;

    @JsonProperty("flg_treasury_branch")
    private FlagYesNo treasuryBranch;

    @JsonProperty("flg_forex_treasury")
    private FlagYesNo forexTreasury;

    @JsonProperty("flg_currency_chest")
    private FlagYesNo currencyChest;

    @JsonProperty("flg_small_coin_depot")
    private FlagYesNo smallCoinDepot;

    @JsonProperty("flg_asset_recovery_branch")
    private FlagYesNo assetRecoveryBranch;

    @JsonProperty("flg_clearing_payment_svc")
    private FlagYesNo clearingPaymentSvc;

    @JsonProperty("flg_deposit_processing_center")
    private FlagYesNo depositProcessingCenter;

    @JsonProperty("flg_loan_processing_center")
    private FlagYesNo loanProcessingCenter;

    @JsonProperty("flg_forex_processing_center")
    private FlagYesNo forexProcessingCenter;

    @JsonProperty("flg_trade_fin_processing_center")
    private FlagYesNo tradeFinProcessingCenter;

    @JsonProperty("flg_administrative_office")
    private FlagYesNo administrativeOffice;

    @JsonProperty("flg_extension_counter")
    private FlagYesNo extensionCounter;

    @JsonProperty("flg_satellite_office")
    private FlagYesNo satelliteOffice;

    @JsonProperty("flg_mobile_office")
    private FlagYesNo mobileOffice;

    @JsonProperty("flg_service_branch")
    private FlagYesNo serviceBranch;

    @JsonProperty("flg_mobile_atm")
    private FlagYesNo mobileAtm;

    @JsonProperty("flg_onsite_atm")
    private FlagYesNo onsiteAtm;

    @JsonProperty("flg_offsite_atm")
    private FlagYesNo offsiteAtm;

    @JsonProperty("flg_rep_office")
    private FlagYesNo repOffice;

    @JsonProperty("flg_exchg_bureau")
    private FlagYesNo exchangeBureau;

    @JsonProperty("enu_auth_forex_dealer_category")
    private String authForexDealerCategory;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonProperty("dat_forex_authorized")
    private LocalDateTime forexAuthorized;
}
