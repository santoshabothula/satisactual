package com.datawise.satisactual.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Embeddable
@Getter
@Setter
public class UserAccessLogEmbeddedKey implements Serializable {

    @Serial
    private static final long serialVersionUID = 1510906139114168498L;

    @Column(name = "txt_login_id")
    private String txtLoginId;

    @Column(name = "dat_time_login")
    private Date datTimeLogin;

}
