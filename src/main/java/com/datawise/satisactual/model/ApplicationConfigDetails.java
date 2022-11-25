package com.datawise.satisactual.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplicationConfigDetails {

    private String DBIPAddress = "";
    private String DBUsername = "";
    private String DBPassword = "";
    private String rptIPAddress = "";
    private String imgIPAddress = "";
    private String inactiveTimeout = "";
    private String failedPwdLimit = "";
    private String alertsEMailID = "";
    private String alertsEMailPWD = "";
    private String alertsEMailProtocol = "";
    private String alertsEMailClient = "";
    private String alertsEMailPort = "";
    private String alertsEMailSSL = "";
    private String DSN = "";
    private String docUploadPath = "";
    private String AVRecordingsPath = "";
    private String GoogleTranslateKey = "";

}
