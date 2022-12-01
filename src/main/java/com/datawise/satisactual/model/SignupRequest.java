package com.datawise.satisactual.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class SignupRequest {

    @JsonProperty("fname")
    private String firstName;

    @JsonProperty("lname")
    private String lastName;

    @JsonProperty("pnumber")
    private String phoneNumber;

    @JsonProperty("email")
    private String email;

    @JsonProperty("organization")
    private String organization;
}
