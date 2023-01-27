package com.datawise.satisactual.controller;

import com.datawise.satisactual.custom.models.*;
import com.datawise.satisactual.entities.BlockedIp;
import com.datawise.satisactual.entities.UserMaster;
import com.datawise.satisactual.model.JwtTokenDetails;
import com.datawise.satisactual.model.UserProfile;
import com.datawise.satisactual.repository.BlockedIpRepository;
import com.datawise.satisactual.repository.LoginVerificationRepository;
import com.datawise.satisactual.repository.UserMasterRepository;
import com.datawise.satisactual.utils.CryptoUtil;
import com.datawise.satisactual.utils.JwtTokenCreator;
import com.datawise.satisactual.utils.LoginVerificationUtil;
import com.datawise.satisactual.utils.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/login")
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@CrossOrigin
public class LoginVerificationController {

    @Autowired
    private UserMasterRepository repository;
    @Autowired
    private BlockedIpRepository blockedIpRepository;
    @Autowired
    private LoginVerificationRepository loginVerificationRepository;
    @Autowired
    private LoginVerificationUtil loginVerificationUtil;
    @Autowired
    private JwtTokenCreator creator;

    int isBlocked = 0;
    public int status = LoginVerificationUtil.STATUS_SUCCESS;

    public String cod_language = "ENG";
    public String lcod_home_module = "LOS";
    public String lcod_home_menu = LoginVerificationUtil.EMPTY;

    @GetMapping
    public ResponseEntity<JwtTokenDetails> loginVerification(HttpServletRequest request) {

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        String password = "";
        Optional<UserMaster> userMaster = repository.findById(userId);
        if (userMaster.isPresent()) {
            // password = new String(Base64.getDecoder().decode(request.getHeader(SecurityConstants.AUTHORIZATION_HEADER.toLowerCase()).replace(LoginVerificationUtil.TXT_BASIC + LoginVerificationUtil.SPACE, LoginVerificationUtil.EMPTY))).replace(userId + LoginVerificationUtil.COLAN, LoginVerificationUtil.EMPTY);
            password = userMaster.get().getTxtUserSignature();
        }

        int allow;
        int num_licensed_modules = 0;
        int num_valid_home_menu = 0;
        int login_attempt;
        String logintype = "success";

        String to_be_splitted_uri = request.getRequestURI();
        String[] splitted_uri = to_be_splitted_uri.split("/", -2);
        to_be_splitted_uri = splitted_uri[1];
        String final_uri = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" + to_be_splitted_uri + "/";

        JwtTokenDetails tokenDetails = new JwtTokenDetails();
        verifyBlockedIP(request);

        // password = CryptoUtil.getEncryptedPassword(password);
        UserLoginDetails userLoginDetails = loginVerificationRepository.findByCodRecStatusAndTxtLoginIdAndTxtUserSignature(3, userId, password);
        if (Objects.isNull(userLoginDetails)) throw new RuntimeException("User not found");

        UserProfile userProfile = populateUserDetails(userLoginDetails);
        lcod_home_module = userLoginDetails.getCodHomeModule();
        cod_language = userLoginDetails.getCodLanguage();
        login_attempt = userLoginDetails.getNumFailedPwd() + 1;
        lcod_home_menu = userLoginDetails.getCodHomeMenu();
        if (lcod_home_module.equalsIgnoreCase("")) {
            lcod_home_module = "LOS";
        }

        allow = loginVerificationUtil.validateLoginRequest(request, userId, userLoginDetails, status, isBlocked);
        if (allow == LoginVerificationUtil.STATUS_SUCCESS) {
            UserSysInstallModuleDetails userSysInstallModuleDetails = loginVerificationRepository.findUserSysInstallModules(lcod_home_module);
            if (Objects.nonNull(userSysInstallModuleDetails)) num_licensed_modules++;
            num_valid_home_menu = makeHomeLink(tokenDetails, userSysInstallModuleDetails, num_valid_home_menu, userId);

            if (num_licensed_modules > 0 && num_valid_home_menu > 0) {
                processUserOtherDetails(tokenDetails, userId);
                tokenDetails.setUserProfile(userProfile);
            } else {
                logintype = "fail";
                allow = LoginVerificationUtil.STATUS_LIC_EXPIRED;
            }
        } else if (allow == LoginVerificationUtil.STATUS_PWD_EXPIRED) {
            logintype = "chgpass";
        } else {
            logintype = "fail";
            login_attempt++;
            loginVerificationRepository.updateUserLoginFailed(login_attempt, userId);
        }

        loginVerificationUtil.logAccessRequest(userId, logintype, allow, loginVerificationUtil.getClientIp(request), LoginVerificationUtil.getClientBrowser(request), LoginVerificationUtil.getClientOs(request), final_uri);

        tokenDetails.setStatusCode(logintype);
        tokenDetails.setStatusMessage(loginVerificationUtil.loginStatusDesc(allow));
        userProfile.setCodLoginStatus(allow);
        userProfile.setTxtLoginStatus(tokenDetails.getStatusMessage());
        tokenDetails.setCodLoginStatus(allow);
        tokenDetails.setTxtLoginStatus(tokenDetails.getStatusMessage());
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (logintype.equalsIgnoreCase("success")) {
            creator.generateToken(request, tokenDetails);
            httpStatus = HttpStatus.OK;
        }
        return ResponseEntity.status(httpStatus).body(tokenDetails);
    }

    private int makeHomeLink(JwtTokenDetails tokenDetails, UserSysInstallModuleDetails userSysInstallModuleDetails, int num_valid_home_menu, String userId) {
        String txt_module_home_page = "";
        String txt_module_param_1 = "";
        String txt_module_param_2 = "";
        String txt_module_param_3 = "";
        if (Objects.nonNull(userSysInstallModuleDetails)) {
            txt_module_home_page = userSysInstallModuleDetails.getTxtModuleHoePage();
            txt_module_param_1 = userSysInstallModuleDetails.getTxtModulePara1();
            txt_module_param_2 = userSysInstallModuleDetails.getTxtModulePara2();
            txt_module_param_3 = userSysInstallModuleDetails.getTxtModulePara3();
        }

        if (lcod_home_menu.equalsIgnoreCase("")) {
            String txt_homepage_link = null;
            if (txt_module_param_2 == null && txt_module_param_3 == null) {
                txt_homepage_link = txt_module_home_page + "" + txt_module_param_1;
            } else if (txt_module_param_2 != null) {
                if (txt_module_param_3 == null) {
                    txt_homepage_link = txt_module_home_page + "" + txt_module_param_1 + "" + txt_module_param_2;
                }
            } else if (txt_module_param_3 != null) {
                if (txt_module_param_2 == null) {
                    txt_homepage_link = txt_module_home_page + "" + txt_module_param_1 + "" + txt_module_param_3;
                }
            } else {
                txt_homepage_link = txt_module_home_page + "" + txt_module_param_1 + "" + txt_module_param_2 + "" + txt_module_param_3;
            }
            tokenDetails.setTxtHomepageLink(txt_homepage_link);
        } else {
            MenuParamDetails menuOptions = loginVerificationRepository.findUserMenuParams(lcod_home_menu, userId);
            num_valid_home_menu++;
            if (Objects.nonNull(menuOptions)) {
                String txt_menu_param_1 = menuOptions.getTxtMenuParam1();
                String txt_menu_param_2 = menuOptions.getTxtMenuParam2();
                String txt_menu_param_3 = menuOptions.getTxtMenuParam3();
                String txt_homepage_link = txt_menu_param_1 + "" + txt_menu_param_2 + "" + txt_menu_param_3;
                tokenDetails.setTxtHomepageLink(txt_homepage_link);
            }
        }
        return num_valid_home_menu;
    }

    private void processUserOtherDetails(JwtTokenDetails tokenDetails, String userId) {
        LinkedHashMap<String, String> spanOfControl = new LinkedHashMap<>();
        List<UserSpanOfControlDetails> userSpanOfControlDetails = loginVerificationRepository.findUserSpanOfControls(userId);
        if (Objects.nonNull(userSpanOfControlDetails) && !userSpanOfControlDetails.isEmpty()) {
            String cod_org_unit = userSpanOfControlDetails.get(0).getCodOrgUnit();
            String txt_org_unit_name = userSpanOfControlDetails.get(0).getTxtOrgUnitName();
            spanOfControl.put(cod_org_unit, txt_org_unit_name);
            tokenDetails.setSpanOfControl(spanOfControl);
        }

        LinkedHashMap<String, String> usersManaged = new LinkedHashMap<>();
        userSpanOfControlDetails = loginVerificationRepository.findUserManaged(userId);
        if (Objects.nonNull(userSpanOfControlDetails) && !userSpanOfControlDetails.isEmpty()) {
            String txt_managed_user_id = userSpanOfControlDetails.get(0).getTxtLoginId();
            String txt_user_name = userSpanOfControlDetails.get(0).getTxtUserFname() + " " + userSpanOfControlDetails.get(0).getTxtUserLname();
            usersManaged.put(txt_managed_user_id, txt_user_name);
            tokenDetails.setUsersManaged(usersManaged);
        }

        LinkedHashMap<Integer, String> centersManaged = new LinkedHashMap<>();
        userSpanOfControlDetails = loginVerificationRepository.findUserCentersManaged(userId);
        if (Objects.nonNull(userSpanOfControlDetails) && !userSpanOfControlDetails.isEmpty()) {
            Integer id_center = userSpanOfControlDetails.get(0).getIdCenter();
            String txt_center_name = userSpanOfControlDetails.get(0).getTxtCenterName();
            centersManaged.put(id_center, txt_center_name);
            tokenDetails.setCentersManaged(centersManaged);
        }

        LinkedHashMap<Integer, String> userMessages = new LinkedHashMap<>();
        List<UserMessageDetails> messageDetails = loginVerificationRepository.findUserMessages(cod_language);
        if (Objects.nonNull(messageDetails) && messageDetails.size() > 0) {
            messageDetails.forEach(message -> {
                Integer num_message = message.getNumMessage();
                String txt_user_message = message.getTxtUserMessage();
                userMessages.put(num_message, txt_user_message);
            });
            tokenDetails.setUserMessages(userMessages);
        }
        loginVerificationRepository.updateUserLoginSuccess(userId);
    }

    private void verifyBlockedIP(HttpServletRequest request) {
        String txtCurrentSessionIP = loginVerificationUtil.getClientIp(request);
        Optional<BlockedIp> blockedIp = blockedIpRepository.findById("A");
        if (blockedIp.isPresent()) {
            String txtCurrentIpPrefix = "";
            String txtBlockedIpPrefix = blockedIp.get().getTxtBlockedIpString().substring(0, blockedIp.get().getTxtBlockedIpString().lastIndexOf("."));
            if (txtCurrentSessionIP.lastIndexOf(".") > 0) {
                txtCurrentIpPrefix = txtCurrentSessionIP.substring(0, txtCurrentSessionIP.lastIndexOf("."));
            }
            if (txtCurrentIpPrefix.equalsIgnoreCase(txtBlockedIpPrefix)) {
                isBlocked = 1;
                status = LoginVerificationUtil.STATUS_BLOCKED_IP;
            }
        }
    }

    private UserProfile populateUserDetails(UserLoginDetails details) {
        return UserProfile.builder()
                .txtLoginId(details.getTxtLoginId())
                .txtUserSignature(details.getTxtUserSignature())
                .flgFunctionalId(details.getFlgFunctionalId())
                .flgForcePasswdChg(details.getFlgForcePasswdChg())
                .datLastPasswdChg(details.getDatLastPasswdChg())
                .txtUserFName(details.getTxtUserFname())
                .txtUserMName(details.getTxtUserMname())
                .txtUserLName(details.getTxtUserLname())
                .enuUserGender(details.getEnuUserGender())
                .datUserBirth(details.getDatUserBirth())
                .idParentCompany3rdParty(details.getIdParentCompany3rdparty())
                .txtParentCompanyName(details.getTxtParentCompanyName())
                .flgParentCompanyVerified(details.getFlgParentCompanyVerified())
                .datTimeParentCompanyVerified(details.getDatTimeParentCompanyVerified())
                .codLanguage(details.getCodLanguage())
                .txtUserEmailId(details.getTxtUserEmailId())
                .txtUserMobilePhone(details.getTxtUserMobilePhone())
                .txtDeviceAssigned(details.getTxtDeviceAssigned())
                .codHomeModule(details.getCodHomeModule())
                .codHomeMenu(details.getCodHomeMenu())
                .codDepartment(details.getCodDepartment())
                .codUserDesignation(details.getCodUserDesignation())
                .txtManagerLoginId(details.getTxtManagerLoginId())
                .txtDefaultReviewerId(details.getTxtDefaultReviewerId())
                .txtCreditApproveId(details.getTxtCreditApproverId())
                .codHomeBranch(details.getCodHomeBranch())
                .flgDisabled(details.getFlgDisabled())
                .datLastLogin(details.getDatLast_login())
                .numFailedPwd(details.getNumFailedPwd())
                .datProfileExpiry(details.getDatProfileExpiry())
                .numTimeStartWkDay(details.getNumTimeStartWkday())
                .numTimeEndWkDay(details.getNumTimeEndWkday())
                .numTimeStartWkEnd(details.getNumTimeStartWkend())
                .numTimeEndWkEnd(details.getNumTimeEndWkend())
                .numTimeStartHoliday(details.getNumTimeStartHoliday())
                .numTimeEndHoliday(details.getNumTimeEndHoliday()).build();

    }
}
