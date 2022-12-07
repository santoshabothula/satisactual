package com.datawise.satisactual.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
@JsonIgnoreProperties
public class CampaignDetails {

    @JsonProperty("txt_campaign_name")
    private String txtCampaignName;

    @JsonProperty("txt_campaign_type_desc")
    private String txtCampaignTypeDesc;

    @JsonProperty("id_campaign")
    private BigInteger idCampaign;

    @JsonProperty("cod_campaign_type")
    private String codCampaignType;

    @JsonProperty("txt_campaign_short_code")
    private String txtCampaignShortCode;

    @JsonProperty("flg_current_campaign")
    private String flgCurrentCampaign;

    @JsonProperty("dat_start")
    private Date datStart;

    @JsonProperty("dat_end")
    private Date datEnd;

    @JsonProperty("txt_campaign_title")
    private String txtCampaignTitle;

    @JsonProperty("txt_campaign_subtitle")
    private String txtCampaignSubtitle;

    @JsonProperty("enu_campaign_status")
    private String enuCampaignStatus;

    @JsonProperty("txt_camp_mgr_id")
    private String txtCampMgrId;

    @JsonProperty("txt_camp_mgr_name")
    private String txtCampMgrName;

    @JsonProperty("id_commissioned_by_thirdparty")
    private BigInteger idCommissionedByThirdParty;

    @JsonProperty("txt_third_party_name")
    private String txtThirdPartyName;

    @JsonProperty("txt_third_party_short_name")
    private String txtThirdPartyShortName;

    @JsonProperty("bin_logo_to_display")
    private String binLogoToDisplay;
}
