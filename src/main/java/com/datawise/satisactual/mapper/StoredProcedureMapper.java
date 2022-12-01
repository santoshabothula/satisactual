package com.datawise.satisactual.mapper;

import com.datawise.satisactual.model.CampaignDetails;
import com.datawise.satisactual.model.WaveDetails;
import org.springframework.util.CollectionUtils;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class StoredProcedureMapper {

    public static List<CampaignDetails> mapCampaigns(List<Tuple> rows) {
        List<CampaignDetails> campaignDetails = new ArrayList<>();
        if (CollectionUtils.isEmpty(rows)) return campaignDetails;
        return rows.stream().map(StoredProcedureMapper::mapCampaign).collect(Collectors.toList());
    }

    public static CampaignDetails mapCampaign(Tuple row) {
        CampaignDetails campaignDetails = new CampaignDetails();
        campaignDetails.setTxtCampaignName((String) row.get("txt_campaign_name"));
        campaignDetails.setTxtCampaignTypeDesc((String) row.get("txt_campaign_type_desc"));
        campaignDetails.setIdCampaign((BigInteger) row.get("id_campaign"));
        campaignDetails.setCodCampaignType((String) row.get("cod_campaign_type"));
        campaignDetails.setTxtCampaignShortCode((String) row.get("txt_campaign_short_code"));
        campaignDetails.setFlgCurrentCampaign((String) row.get("flg_current_campaign"));
        campaignDetails.setDatStart((Date) row.get("dat_start"));
        campaignDetails.setDatEnd((Date) row.get("dat_end"));
        campaignDetails.setTxtCampaignTitle((String) row.get("txt_campaign_title"));
        campaignDetails.setTxtCampaignSubtitle((String) row.get("txt_campaign_subtitle"));
        campaignDetails.setEnuCampaignStatus(String.valueOf(row.get("enu_campaign_status")));
        campaignDetails.setTxtCampMgrId((String) row.get("txt_camp_mgr_id"));
        campaignDetails.setTxtCampMgrName((String) row.get("txt_camp_mgr_name"));
        campaignDetails.setIdCommissionedByThirdparty((BigInteger) row.get("id_commissioned_by_thirdparty"));
        campaignDetails.setTxtThirdPartyName((String) row.get("txt_third_party_name"));
        campaignDetails.setTxtThirdPartyShortName((String) row.get("txt_third_party_short_name"));
        campaignDetails.setBinLogoToDisplay((String) row.get("bin_logo_to_display"));
        return campaignDetails;
    }

    public static List<WaveDetails> mapWaves(List<Tuple> rows) {
        List<WaveDetails> waveDetails = new ArrayList<>();
        if (CollectionUtils.isEmpty(rows)) return waveDetails;
        return rows.stream().map(StoredProcedureMapper::mapWave).collect(Collectors.toList());
    }

    public static WaveDetails mapWave(Tuple row) {
        WaveDetails waveDetails = new WaveDetails();
        waveDetails.setIdCampaign((BigInteger) row.get("id_campaign"));
        waveDetails.setIdCampaignWave((BigInteger) row.get("id_campaign_wave"));
        waveDetails.setTxtCampaignWaveName(String.valueOf(row.get("txt_campaign_wave_name")));
        waveDetails.setIdContactList((BigInteger) row.get("id_contact_list"));
        waveDetails.setTxtContactListName(String.valueOf(row.get("txt_contact_list_name")));
        waveDetails.setDatWaveStart((Date) row.get("dat_wave_start"));
        waveDetails.setDatWaveEnd((Date) row.get("dat_wave_end"));
        waveDetails.setFlgCurrentWave(String.valueOf(row.get("flg_current_wave")));
        waveDetails.setFlgFutureWave(String.valueOf(row.get("flg_future_wave")));
        waveDetails.setFlgExpiredWave(String.valueOf(row.get("flg_expired_wave")));
        waveDetails.setTxtWaveDeliveryHeadId(String.valueOf(row.get("txt_wave_delivery_head_id")));
        waveDetails.setTxtWaveRequisitionerId(String.valueOf(row.get("txt_wave_requisitioner_id")));
        waveDetails.setIdQuestionnaire((BigInteger) row.get("id_questionnaire"));
        waveDetails.setTxtQuestionnaireName(String.valueOf(row.get("txt_questionnaire_name")));
        waveDetails.setFlgAssessment(String.valueOf(row.get("flg_assessment")));
        waveDetails.setTxtPrintFormatFile(String.valueOf(row.get("txt_print_format_file")));
        waveDetails.setNumTotResponseReqd((Integer) row.get("num_tot_response_reqd"));
        waveDetails.setNumEscalationLevels((Short) row.get("num_escalation_levels"));
        waveDetails.setNumHoursToEscalation((Short) row.get("num_hours_to_escalation"));
        waveDetails.setNumLowRatingBelow((Short) row.get("num_low_rating_below"));
        waveDetails.setNumHighRatingAbove((Short) row.get("num_high_rating_above"));
        waveDetails.setCodCssTheme(String.valueOf(row.get("cod_css_theme")));
        waveDetails.setFlgTestWave(String.valueOf(row.get("flg_test_wave")));
        return waveDetails;
    }
}
