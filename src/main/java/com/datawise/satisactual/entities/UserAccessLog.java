package com.datawise.satisactual.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SEC_USER_ACCESS_LOG")
@Getter
@Setter
public class UserAccessLog {

    @EmbeddedId
    private UserAccessLogEmbeddedKey id;

    @Column(name = "FLG_SUCCESS")
    private String flgSuccess;

    @Column(name = "TXT_IP_ADDRESS_SOURCE")
    private String txtIpAddressSource;

    @Column(name = "TXT_BROWSER_USED")
    private String txtBrowserUsed;

    @Column(name = "TXT_OS_USED")
    private String txtOsUsed;

    @Column(name = "TXT_USER_AGENT_STRING")
    private String txtUserAgentString;

    @Column(name = "TXT_LOGIN_FAIL_REASON")
    private String txtLoginFailReason;

}
