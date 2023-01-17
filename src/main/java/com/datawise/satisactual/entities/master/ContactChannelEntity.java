package com.datawise.satisactual.entities.master;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mst_contact_channel")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactChannelEntity extends MakerCheckerEntity {

    @EmbeddedId
    private ContactChannelEmbeddedKey id;

    @Column(name = "txt_channel_desc")
    private String channelDesc;

    @Column(name = "flg_default_value")
    private String defaultValue;

    @Column(name = "enu_base_channel")
    private String baseChannel;

    @Column(name = "flg_email_supported")
    private String emailSupported;

    @Column(name = "txt_email_id_for_send")
    private String emailIdForSend;

    @Column(name = "txt_email_signature")
    private String emailSignature;

    @Column(name = "txt_email_signature_2fa")
    private String emailSignature2fa;

    @Column(name = "txt_smtp_client")
    private String smtpClient;

    @Column(name = "flg_enable_ssl")
    private String enableSsl;

    @Column(name = "num_smtp_port")
    private Integer smtpPort;

    @Column(name = "flg_SMS_supported")
    private String SMSSupported;

    @Column(name = "flg_auto_dialer_supported")
    private String autoDialerSupported;

    @Column(name = "cod_auto_dialer")
    private String autoDialer;

    @Column(name = "enu_mail_protocol")
    private String mailProtocol;

    @Column(name = "txt_pop3_username")
    private String pop3Username;

    @Column(name = "txt_pop3_client")
    private String pop3Client;

    @Column(name = "num_pop3_port")
    private Integer pop3Port;

    @Column(name = "txt_pop3_encryption")
    private String pop3Encryption;

    @Column(name = "txt_imap_username")
    private String imapUsername;

    @Column(name = "txt_imap_client")
    private String imapClient;

    @Column(name = "num_imap_port")
    private Integer imapPort;

    @Column(name = "txt_imap_encryption")
    private String imapEncryption;
}