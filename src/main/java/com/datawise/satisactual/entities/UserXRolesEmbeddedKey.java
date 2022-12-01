package com.datawise.satisactual.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserXRolesEmbeddedKey implements Serializable {

    @Column(name = "txt_login_id")
    private String txtLoginId;

    @Column(name = "cod_user_role")
    private String codUserRole;

    @Column(name = "cod_rec_status")
    private String codRecStatus;

}
