package com.datawise.satisactual.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_user_messages")
@Getter
@Setter
public class UserMessage {

    @EmbeddedId
    private UserMessageEmbeddedKey id;

    @Column(name = "txt_user_message")
    private String txtUserMessage;

    @Column(name = "flg_use_as_alert")
    private String flgUseAsAlert;

    @Column(name = "txt_btn_ok")
    private String txtBtnOk;

    @Column(name = "txt_btn_cancel")
    private String txtBtnCancel;

    @Column(name = "txt_btn_other_1")
    private String txtBtnOther1;

    @Column(name = "cod_rec_status")
    private String codRecStatus;

    @Column(name = "txt_last_maker_id")
    private String txtLastMakerId;

    @Column(name = "dat_last_maker")
    private String datLastMaker;

    @Column(name = "txt_last_checker_id")
    private String txtLastCheckerId;

    @Column(name = "dat_last_checker")
    private String datLastChecker;

}
