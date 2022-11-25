package com.datawise.satisactual.model;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
public class WaveDetails {
    private BigInteger idCampaign;
    private BigInteger idCampaignWave;
    private String txtCampaignWaveName;
    private BigInteger idContactList;
    private String txtContactListName;
    private Date datWaveStart;
    private Date datWaveEnd;
    private String flgCurrentWave;
    private String flgFutureWave;
    private String flgExpiredWave;
    private String txtWaveDeliveryHeadId;
    private String txtWaveRequisitionerId;
    private BigInteger idQuestionnaire;
    private String txtQuestionnaireName;
    private String flgAssessment;
    private String txtPrintFormatFile;
    private Integer numTotResponseReqd;
    private Short numEscalationLevels;
    private Short numHoursToEscalation;
    private Short numLowRatingBelow;
    private Short numHighRatingAbove;
    private String codCssTheme;
    private String flgTestWave;
}
