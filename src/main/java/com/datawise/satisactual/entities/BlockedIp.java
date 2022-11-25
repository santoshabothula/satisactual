package com.datawise.satisactual.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "sec_blocked_ip")
@Getter
@Setter
public class BlockedIp {

    @Id
    @Column(name = "cod_rec_status")
    private String codRecStatus;

    @Column(name = "txt_blocked_ip_string")
    private String txtBlockedIpString;

    @Column(name = "txt_blocked_reason")
    private String txtBlockedReason;

    @Column(name = "txt_last_maker_id")
    private String txtLastMakerId;

    @Column(name = "dat_last_maker")
    private Date datLastMaker;

    @Column(name = "txt_last_checker_id")
    private String txtLastCheckerId;

    @Column(name = "dat_last_checker")
    private Date datLastChecker;

}
