package com.datawise.satisactual.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties
public class UserProfile {
    @JsonProperty("flg_disabled")
    private String flgDisabled;

    @JsonProperty("cod_home_branch")
    private String codHomeBranch;

    @JsonProperty("dat_last_passwd_chg")
    private String datLastPasswdChg;

    @JsonProperty("txt_user_lname")
    private String txtUserLName;

    @JsonProperty("num_time_end_holiday")
    private Integer numTimeEndHoliday;

    @JsonProperty("num_time_start_wkend")
    private Integer numTimeStartWkEnd;

    @JsonProperty("enu_user_gender")
    private String enuUserGender;

    @JsonProperty("num_time_end_wkday")
    private Integer numTimeEndWkDay;

    @JsonProperty("num_time_start_wkday")
    private Integer numTimeStartWkDay;

    @JsonProperty("num_time_end_wkend")
    private Integer numTimeEndWkEnd;

    @JsonProperty("txt_user_fname")
    private String txtUserFName;

    @JsonProperty("txt_login_id")
    private String txtLoginId;

    @JsonProperty("cod_home_module")
    private String codHomeModule;

    @JsonProperty("txt_user_mobile_phone")
    private String txtUserMobilePhone;

    @JsonProperty("txt_manager_login_id")
    private String txtManagerLoginId;

    @JsonProperty("txt_default_reviewer_id")
    private String txtDefaultReviewerId;

    @JsonProperty("id_parent_company_3rdparty")
    private String idParentCompany3rdParty;

    @JsonProperty("txt_user_signature")
    private String txtUserSignature;

    @JsonProperty("txt_parent_company_name")
    private String txtParentCompanyName;

    @JsonProperty("cod_department")
    private String codDepartment;

    @JsonProperty("txt_credit_approver_id")
    private String txtCreditApproveId;

    @JsonProperty("dat_profile_expiry")
    private String datProfileExpiry;

    @JsonProperty("cod_user_designation")
    private String codUserDesignation;

    @JsonProperty("dat_last_login")
    private String datLastLogin;

    @JsonProperty("txt_device_assigned")
    private String txtDeviceAssigned;

    @JsonProperty("dat_time_parent_company_verified")
    private String datTimeParentCompanyVerified;

    @JsonProperty("flg_force_passwd_chg")
    private String flgForcePasswdChg;

    @JsonProperty("flg_parent_company_verified")
    private String flgParentCompanyVerified;

    @JsonProperty("txt_user_mname")
    private String txtUserMName;

    @JsonProperty("dat_user_birth")
    private String datUserBirth;

    @JsonProperty("num_failed_pwd")
    private Integer numFailedPwd;

    @JsonProperty("flg_functional_id")
    private String flgFunctionalId;

    @JsonProperty("txt_user_email_id")
    private String txtUserEmailId;

    @JsonProperty("cod_language")
    private String codLanguage;

    @JsonProperty("num_time_start_holiday")
    private Integer numTimeStartHoliday;

    @JsonProperty("cod_home_menu")
    private String codHomeMenu;

    @JsonProperty("cod_login_status")
    private Integer codLoginStatus;

    @JsonProperty("txt_login_status")
    private String txtLoginStatus;
}
