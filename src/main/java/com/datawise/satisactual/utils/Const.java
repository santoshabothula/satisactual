package com.datawise.satisactual.utils;

public interface Const {
    String EMPTY = "";
    String SPACE = " ";
    String UNDERSCORE = "_";
    String NULL = null;
    String AND = "&";
    String COLON = ":";
    String DOT = ".";

    String LANG_ENG_CODE = "ENG";

    Integer INDICATOR_0 = 0;
    Integer INDICATOR_1 = 1;
    String INDICATOR_MINUS_1 = "-1";
    String INDICATOR_A = "A";
    String INDICATOR_N = "N";
    String BACKWARD_SLASH = "/";

    Integer MESSAGE_NUM_1142 = 1142;
    Integer MESSAGE_NUM_1111 = 1111;

    String SIGNUP_ERROR_MESSAGE = "Failed to create / register user";
    String SIGNUP_ERROR_MSG_COD_USER_ROLE_NOT_FOUND = "Code User Role not found!";
    String SIGNUP_ERROR_MSG_SYSTEM_DATE_NOT_FOUND = "Date system as of not found!";

    String PATH_EXT_INDEX = "/index.jsp?check=change"+"&userID=";
    String PATH_EXT_AUTHORIZE = "/user/AuthorizeUser.jsp";

    String LABEL_1700 = "1700";
    String LABEL_0800 = "0800";
    String LABEL_SYSTEM = "SYSTEM";
    String LABEL_D_WISE = "DWISE";
    String LABEL_SATI = "SATI";
    String LABEL_TXT_USER_EMAIL_ID = "txt_user_email_id";
    String LABEL_DAT_SYSTEM_AS_OF = "dat_system_asof";
    String LABEL_DAT_SYSTEM_AS_OF_CAMEL_CASE = "DatSystemAsof";
    String LABEL_USER_EMAIL_ID = "user_email_id";

    String MAIL_TEMPLATE_PARAM_MESSAGE = "message";

    String MAIL_TEMPLATE_WELCOME_SIGNUP = "welcome-generated-password.ftl";
}
