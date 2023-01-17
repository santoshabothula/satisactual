package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactChannelDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_channel")
    private String codChannel;

    @Size(min = 1, max = 24)
	@JsonProperty("txt_channel_desc")
    private String channelDesc;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;

    @Size(min = 1, max = 4)
	@JsonProperty("enu_base_channel")
    private String baseChannel;

	@JsonProperty("flg_email_supported")
    private FlagYesNo emailSupported;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_email_id_for_send")
    private String emailIdForSend;

    @Size(min = 1, max = 128)
	@JsonProperty("txt_email_signature")
    private String emailSignature;

    @Size(min = 1, max = 128)
	@JsonProperty("txt_email_signature_2fa")
    private String emailSignature2fa;

    @Size(min = 1, max = 255)
	@JsonProperty("txt_smtp_client")
    private String smtpClient;

	@JsonProperty("flg_enable_ssl")
    private FlagYesNo enableSsl;

	@JsonProperty("num_smtp_port")
    private Integer smtpPort;

	@JsonProperty("flg_SMS_supported")
    private FlagYesNo SMSSupported;

	@JsonProperty("flg_auto_dialer_supported")
    private FlagYesNo autoDialerSupported;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_auto_dialer")
    private String autoDialer;

    @Size(min = 1, max = 1)
	@JsonProperty("enu_mail_protocol")
    private String mailProtocol;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_pop3_username")
    private String pop3Username;

    @Size(min = 1, max = 255)
	@JsonProperty("txt_pop3_client")
    private String pop3Client;

	@JsonProperty("num_pop3_port")
    private Integer pop3Port;

	@JsonProperty("txt_pop3_encryption")
    private String pop3Encryption;

    @Size(min = 1, max = 4)
	@JsonProperty("txt_imap_username")
    private String imapUsername;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_imap_client")
    private String imapClient;

    @Size(min = 1, max = 255)
	@JsonProperty("num_imap_port")
    private Integer imapPort;

    @Size(min = 1, max = 4)
	@JsonProperty("txt_imap_encryption")
    private String imapEncryption;
}
