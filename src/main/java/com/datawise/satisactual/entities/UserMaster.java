package com.datawise.satisactual.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "SEC_USER_MASTER")
@Getter @Setter
public class UserMaster {

    @Id
    @Column(name = "txt_login_id")
    private String txtLoginId;

    @Column(name = "txt_user_signature")
    private String txtUserSignature;

    @Column(name = "flg_functional_id")
    private String flgFunctionalId;

    @Column(name = "flg_force_passwd_chg")
    private String flgForcePasswdChg;

    @Column(name = "dat_last_passwd_chg")
    private Date datLastPasswdChg;

    @Column(name = "txt_user_fname")
    private String txtUserFname;

    @Column(name = "txt_user_mname")
    private String txtUserMname;

    @Column(name = "txt_user_lname")
    private String txtUserLname;

    @Column(name = "enu_user_gender")
    private String enuUserGender;

    @Column(name = "dat_user_birth")
    private Date datUserBirth;

    @Column(name = "id_parent_company_3rdparty")
    private BigInteger idParentCompany3rdparty;

    @Column(name = "txt_parent_company_name")
    private String txtParentCompanyName;

    @Column(name = "flg_parent_company_verified")
    private String flgParentCompanyVerified;

    @Column(name = "dat_time_parent_company_verified")
    private Date datTimeparentCompanyVerified;

    @Column(name = "txt_parent_company_verified_by")
    private String txtParentCompanyVerifiedBy;

    @Column(name = "txt_employee_id")
    private String txtEmployeeId;

    @Column(name = "cod_language")
    private String codLanguage;

    @Column(name = "txt_user_email_id")
    private String txtUserEmailId;

    @Column(name = "txt_user_mobile_phone")
    private String txtUserMobilePhone;

    @Column(name = "txt_device_assigned")
    private String txtDeviceAssigned;

    @Column(name = "txt_fcm_token")
    private String txtFcmToken;

    @Column(name = "cod_home_module")
    private String codHomeModule;

    @Column(name = "cod_home_menu")
    private String codHomeMenu;

    @Column(name = "cod_user_designation")
    private String codUserDesignation;

    @Column(name = "cod_department")
    private String codDepartment;

    @Column(name = "cod_credit_officer_level")
    private String codCreditOfficerLevel;

    @Column(name = "txt_manager_login_id")
    private String txtManagerLoginId;

    @Column(name = "txt_default_reviewer_id")
    private String txtDefaultReviewerId;

    @Column(name = "txt_credit_approver_id")
    private String txtCreditApproverId;

    @Column(name = "cod_home_branch")
    private String codHomeBranch;

    @Column(name = "flg_disabled")
    private String flgDisabled;

    @Column(name = "flg_user_logged_in")
    private String flgUserLoggedIn;

    @Column(name = "dat_last_login")
    private Date datLastLogin;

    @Column(name = "num_failed_pwd")
    private Integer numFailedPwd;

    @Column(name = "dat_profile_created")
    private Date datProfileCreated;

    @Column(name = "dat_profile_expiry")
    private Date datProfileExpiry;

    @Column(name = "num_time_start_wkday")
    private Integer numTimeStartWkday;

    @Column(name = "num_time_end_wkday")
    private Integer numTimeEndWkday;

    @Column(name = "num_time_start_wkend")
    private Integer numTimeStartWkend;

    @Column(name = "num_time_end_wkend")
    private Integer numTimeEndWkend;

    @Column(name = "num_time_start_holiday")
    private Integer numTimeStartHoliday;

    @Column(name = "num_time_end_holiday")
    private Integer numTimeEndHoliday;

    @Column(name = "cod_2fa_question_1")
    private String cod2faQuestion1;

    @Column(name = "txt_2fa_answer_1")
    private String txt2faAnswer1;

    @Column(name = "cod_2fa_question_2")
    private String cod2faQuestion2;

    @Column(name = "txt_2fa_answer_2")
    private String txt2faAnswer2;

    @Column(name = "cod_2fa_question_3")
    private String cod2faQuestion3;

    @Column(name = "txt_2fa_answer_3")
    private String txt2faAnswer3;

    @Column(name = "cod_2fa_question_4")
    private String cod2faQuestion4;

    @Column(name = "txt_2fa_answer_4")
    private String txt2faAnswer4;

    @Column(name = "cod_2fa_question_5")
    private String cod2faQuestion5;

    @Column(name = "txt_2fa_answer_5")
    private String txt2faAnswer5;

    @Column(name = "cod_rec_status")
    private String codRecStatus;

    @Column(name = "txt_last_maker_id")
    private String txtLastMakerId;

    @Column(name = "dat_last_maker")
    private Date datLastMaker;

    @Column(name = "txt_last_checker_id")
    private String txtLastCheckerId;

    @Column(name = "dat_last_checker")
    private Date datLastChecker;

}
