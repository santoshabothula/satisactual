package com.datawise.satisactual.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
@JsonIgnoreProperties
public class WaveDetails {

    @JsonProperty("id_campaign")
    private BigInteger idCampaign;

    @JsonProperty("id_campaign_wave")
    private BigInteger idCampaignWave;

    @JsonProperty("txt_campaign_wave_name")
    private String txtCampaignWaveName;

    @JsonProperty("id_contact_list")
    private BigInteger idContactList;

    @JsonProperty("txt_contact_list_name")
    private String txtContactListName;

    @JsonProperty("dat_wave_start")
    private Date datWaveStart;

    @JsonProperty("dat_wave_end")
    private Date datWaveEnd;

    @JsonProperty("flg_current_wave")
    private String flgCurrentWave;

    @JsonProperty("flg_future_wave")
    private String flgFutureWave;

    @JsonProperty("flg_expired_wave")
    private String flgExpiredWave;

    @JsonProperty("txt_wave_delivery_head_id")
    private String txtWaveDeliveryHeadId;

    @JsonProperty("txt_wave_requisitioner_id")
    private String txtWaveRequisitionerId;

    @JsonProperty("id_questionnaire")
    private BigInteger idQuestionnaire;

    @JsonProperty("txt_questionnaire_name")
    private String txtQuestionnaireName;

    @JsonProperty("flg_assessment")
    private String flgAssessment;

    @JsonProperty("txt_print_format_file")
    private String txtPrintFormatFile;

    @JsonProperty("num_tot_response_reqd")
    private Integer numTotResponseReq;

    @JsonProperty("num_escalation_levels")
    private Short numEscalationLevels;

    @JsonProperty("num_hours_to_escalation")
    private Short numHoursToEscalation;

    @JsonProperty("num_low_rating_below")
    private Short numLowRatingBelow;

    @JsonProperty("num_high_rating_above")
    private Short numHighRatingAbove;

    @JsonProperty("cod_css_theme")
    private String codCssTheme;

    @JsonProperty("flg_test_wave")
    private String flgTestWave;
}
