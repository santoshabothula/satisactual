package com.datawise.satisactual.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedHashMap;

@Getter @Setter
public class JwtTokenDetails {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String refreshToken;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String issuedBy;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date issuedDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date validTillDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String statusCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String statusMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String txtHomepageLink;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LinkedHashMap<String, String> spanOfControl;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LinkedHashMap<String, String> usersManaged;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LinkedHashMap<Integer, String> centersManaged;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LinkedHashMap<Integer, String> userMessages;
}
