package com.datawise.satisactual.utils;

import com.datawise.satisactual.custom.models.LastLoginDetails;
import com.datawise.satisactual.custom.models.UserLoginDetails;
import com.datawise.satisactual.exception.SatisActualProcessException;
import com.datawise.satisactual.repository.LoginVerificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

@Slf4j
@Component
public class LoginVerificationUtil {

    @Autowired
    private LoginVerificationRepository loginVerificationRepository;

    public final static String EMPTY = "";
    public final static String SPACE = " ";
    public final static String COLAN = ":";
    public final static String TXT_BASIC = "Basic";

    public static  final int STATUS_LIC_EXPIRED = -5;
    public static  final int STATUS_PWD_EXPIRED = -1;
    public static  final int STATUS_SUCCESS = 0;
    public static  final int STATUS_FAILED_PWD_LIMIT = 1;
    public static  final int STATUS_DISABLED = 2;
    public static  final int STATUS_RESERVED_3 = 3;
    public static  final int STATUS_RESERVED_4 = 4;
    public static  final int STATUS_OUTSIDE_LOGIN_WINDOW = 5;
    public static  final int STATUS_RESERVED_6 = 6;
    public static  final int STATUS_FUNCTIONAL_ID = 7;
    public static  final int STATUS_PROFILE_EXPIRED = 8;
    public static  final int STATUS_ALREADY_LOGGED_IN = 9;
    public static  final int STATUS_BLOCKED_IP = 10;
    public static  final int STATUS_NO_REC_FOUND = 100;
    public static  final int STATUS_INTERNAL_SERVER_ERROR = 500;

    public int validateLoginRequest(HttpServletRequest request, String userID, UserLoginDetails details, int status, int isBlocked) {
        String system_day = system_day();
        int current_time = current_time();

        if (status == 0 && isBlocked == 1) {
            status = STATUS_BLOCKED_IP;
        } else {
            try {
                if (Objects.isNull(details)) status = STATUS_NO_REC_FOUND;

                if (Objects.nonNull(details)) {
                    if (details.getNumFailedPwdLimitExceeded().equalsIgnoreCase("Y")) {
                        status = STATUS_FAILED_PWD_LIMIT;
                    }

                    if ((status == 0) && "Y".equalsIgnoreCase(details.getFlgDisabled())) {
                        status = STATUS_DISABLED;
                    }
                    if ((status == 0) && details.getFlgForcePasswdChg().equalsIgnoreCase("Y") || details.getFlgPasswdExpired().equalsIgnoreCase("Y")) {
                        status = STATUS_PWD_EXPIRED;
                    }
                    if ((status == 0) && details.getFlgFunctionalId().equalsIgnoreCase("Y")) {
                        status = STATUS_FUNCTIONAL_ID;
                    }
                    if ((status == 0) && details.getFlgProfileExpired().equalsIgnoreCase("Y")) {
                        status = STATUS_PROFILE_EXPIRED;
                    }

                    String txtCurrentSessionIP = getClientIp(request);
                    String txtLastLoginIP = getLastLoginIP(userID);

                    String [] CurrentIp = txtCurrentSessionIP.split(",");
                    String [] LastIp = txtLastLoginIP.split(",");

                    if ((status == 0) && details.getFlgUserLoggedIn().equalsIgnoreCase("y") && !(CurrentIp[0].equalsIgnoreCase(LastIp[0]))) {
                        status = STATUS_ALREADY_LOGGED_IN;
                    }

                    if (status == 0) {
                        if (details.getNumIsHoliday() == 1) {
                            if (current_time < details.getNumTimeStartHoliday() || current_time > details.getNumTimeEndHoliday()) {
                                status = STATUS_OUTSIDE_LOGIN_WINDOW;
                            }
                        } else if ("sat".equalsIgnoreCase(system_day) || "sun".equalsIgnoreCase(system_day)) {
                            if (current_time < details.getNumTimeStartWkend() || current_time > details.getNumTimeEndWkend()) {
                                status = STATUS_OUTSIDE_LOGIN_WINDOW;
                            }
                        } else if ("mon".equalsIgnoreCase(system_day) || "tue".equalsIgnoreCase(system_day) || "Wed".equalsIgnoreCase(system_day) || "thu".equalsIgnoreCase(system_day) || "frI".equalsIgnoreCase(system_day)) {
                            if (current_time < details.getNumTimeStartWkday() || current_time > details.getNumTimeEndWkday()) {
                                status = STATUS_OUTSIDE_LOGIN_WINDOW;
                            }
                        }
                    }
                }
            } catch (RuntimeException e) {
                log.error("Runtime Exception", e);
                status = STATUS_INTERNAL_SERVER_ERROR;
            } catch (Exception ex) {
                status = STATUS_INTERNAL_SERVER_ERROR;
                log.error("Status 80 Exception", ex);
            }
        }
        return status;
    }

    public void logAccessRequest(String userID, String loginAttemptStatus, Integer loginAttemptCode, String clientIP, String clientBrowser, String clientOS, String finalURI) {
        try {
            loginVerificationRepository.logUserAccessRequest(
                    userID,
                    loginAttemptStatus.equalsIgnoreCase("fail") ? "N" : "Y",
                    loginAttemptCode.toString() + ":" + loginStatusDesc(loginAttemptCode),
                    clientIP,
                    clientBrowser,
                    clientOS,
                    finalURI
            );
        } catch (Exception e) {
            log.error("Exception while logging user access request", e);
            throw new SatisActualProcessException("Exception while logging user access request");
        }
    }

    public String loginStatusDesc(Integer loginAttemptCode) {
        String txt_login_fail_reason;
        switch (loginAttemptCode) {
            case (STATUS_SUCCESS) -> txt_login_fail_reason = "SUCCESS";
            case (STATUS_LIC_EXPIRED) -> txt_login_fail_reason = "STATUS_LIC_EXPIRED";
            case (STATUS_PWD_EXPIRED) -> txt_login_fail_reason = "STATUS_PWD_EXPIRED";
            case (STATUS_FAILED_PWD_LIMIT) -> txt_login_fail_reason = "STATUS_FAILED_PWD_LIMIT";
            case (STATUS_DISABLED) -> txt_login_fail_reason = "STATUS_DISABLED";
            case (STATUS_RESERVED_3) -> txt_login_fail_reason = "STATUS_RESERVED_3";
            case (STATUS_RESERVED_4) -> txt_login_fail_reason = "STATUS_RESERVED_4";
            case (STATUS_OUTSIDE_LOGIN_WINDOW) -> txt_login_fail_reason = "STATUS_OUTSIDE_LOGIN_WINDOW";
            case (STATUS_RESERVED_6) -> txt_login_fail_reason = "STATUS_RESERVED_6";
            case (STATUS_FUNCTIONAL_ID) -> txt_login_fail_reason = "STATUS_FUNCTIONAL_ID";
            case (STATUS_PROFILE_EXPIRED) -> txt_login_fail_reason = "STATUS_PROFILE_EXPIRED";
            case (STATUS_ALREADY_LOGGED_IN) -> txt_login_fail_reason = "STATUS_ALREADY_LOGGED_IN";
            case (STATUS_BLOCKED_IP) -> txt_login_fail_reason = "STATUS_BLOCKEDIP";
            case (STATUS_NO_REC_FOUND) -> txt_login_fail_reason = "STATUS_NORECFOUND";
            case (STATUS_INTERNAL_SERVER_ERROR) -> txt_login_fail_reason = "INTERNAL_SERVER_ERROR";
            default -> txt_login_fail_reason = loginAttemptCode.toString();
        }
        return txt_login_fail_reason;
    }

    public static String getClientBrowser(HttpServletRequest request) {

        String userAgent = request.getHeader("User-Agent");
        String user = userAgent.toLowerCase();
        String browser = "";

        //===============Browser===========================
        if (user.contains("msie")) {
            String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
            browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
        } else if (user.contains("safari") && user.contains("version")) {
            browser = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0] + "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
        } else if (user.contains("opr") || user.contains("opera")) {
            if (user.contains("opera")) {
                browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0] + "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
            } else if (user.contains("opr")) {
                browser = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
            }
        } else if (user.contains("chrome")) {
            browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
        } else if ((user.contains("mozilla/7.0")) || (user.contains("netscape6")) || (user.contains("mozilla/4.7")) || (user.contains("mozilla/4.78")) || (user.contains("mozilla/4.08")) || (user.contains("mozilla/3"))) {
            //browser=(userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[0]).replace("/", "-");
            browser = "Netscape-?";

        } else if (user.contains("firefox")) {
            browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
        } else if (user.contains("rv")) {
            browser = "IE-" + user.substring(user.indexOf("rv") + 3, user.indexOf(")"));
        } else {
            browser = "UnKnown, More-Info: " + userAgent;
        }

        return browser;
    }

    public static String getClientOs(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        //String user = userAgent.toLowerCase();
        String os;
        //=================OS=======================
        if (userAgent.toLowerCase().contains("windows")) {
            os = "Windows";
        } else if (userAgent.toLowerCase().contains("mac")) {
            os = "Mac";
        } else if (userAgent.toLowerCase().contains("x11")) {
            os = "Unix";
        } else if (userAgent.toLowerCase().contains("android")) {
            os = "Android";
        } else if (userAgent.toLowerCase().contains("iphone")) {
            os = "IPhone";
        } else {
            os = "UnKnown, More-Info: " + userAgent;
        }
        return os;
    }

    public static String system_day() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        return sdf.format(cal.getTime());
    }

    public static int current_time() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("H");
        SimpleDateFormat sdf2 = new SimpleDateFormat("m");
        int min = Integer.parseInt(sdf2.format(cal.getTime()));
        if (min < 10) {
            min *= 10;
        }
        String time = sdf.format(cal.getTime()) + min;
        return Integer.parseInt(time);
    }

    public String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";
        if (Objects.nonNull(request)) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }

    private String getLastLoginIP(String userId) {
        String txtLastLoginIP = "FAIL";
        LastLoginDetails lastLoginDetails = loginVerificationRepository.findLastLogin(userId);

        if (Objects.isNull(lastLoginDetails)) return txtLastLoginIP;

        return "Y".equalsIgnoreCase(lastLoginDetails.getFlgSuccess()) ? lastLoginDetails.getTxtIpAddressSource() : txtLastLoginIP;
    }
}
