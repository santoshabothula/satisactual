package com.datawise.satisactual.model;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
public class CampaignDetails {
    private String txtCampaignName;
    private String txtCampaignTypeDesc;
    private BigInteger idCampaign;
    private String codCampaignType;
    private String txtCampaignShortCode;
    private String flgCurrentCampaign;
    private Date datStart;
    private Date datEnd;
    private String txtCampaignTitle;
    private String txtCampaignSubtitle;
    private String enuCampaignStatus;
    private String txtCampMgrId;
    private String txtCampMgrName;
    private BigInteger idCommissionedByThirdparty;
    private String txtThirdPartyName;
    private String txtThirdPartyShortName;
    private String binLogoToDisplay;
}
