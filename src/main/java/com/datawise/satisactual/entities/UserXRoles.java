package com.datawise.satisactual.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "sec_user_x_roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserXRoles {

    @EmbeddedId
    private UserXRolesEmbeddedKey id;

    @Column(name = "txt_last_maker_id")
    private String txtLastMakerId;

    @Column(name = "dat_last_maker")
    private Date datLastMaker;

    @Column(name = "txt_last_checker_id")
    private String txtLastCheckerId;

    @Column(name = "dat_last_checker")
    private String datLastChecker;

}
