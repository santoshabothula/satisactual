package com.datawise.satisactual.service;

import com.datawise.satisactual.entities.SysGlobalVars;
import com.datawise.satisactual.repository.StoredProcedureRepository;
import com.datawise.satisactual.repository.SysGlobalVarsRepository;
import com.datawise.satisactual.repository.UserMasterRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.Tuple;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class CampaignService {

    @Autowired
    private SysGlobalVarsRepository sysGlobalVarsRepository;
    @Autowired
    private UserMasterRepository userMasterRepository;
    @Autowired
    private StoredProcedureRepository storedProcedureRepository;

    public JSONArray getCampaigns(String username) {
        String lTxtDatSystemAsOf;
        JSONArray campaigns = new JSONArray();
        List<SysGlobalVars> sysGlobalVars = sysGlobalVarsRepository.findByCodRecStatus("A");
        if (!CollectionUtils.isEmpty(sysGlobalVars)) {
            lTxtDatSystemAsOf = sysGlobalVars.get(0).getDatSystemAsof();
        } else {
            lTxtDatSystemAsOf = LocalDate.now().format(DateTimeFormatter.ISO_DATE_TIME);
        }

        List<Tuple> campaignDetails = userMasterRepository.findCampaignsDetails(username, lTxtDatSystemAsOf);
        List<Tuple> myCampaigns = storedProcedureRepository.findMyCampaign(username);

        if (CollectionUtils.isEmpty(myCampaigns)) return campaigns;

        Map<String, List<Tuple>> map = myCampaigns.stream().collect(groupingBy(c -> String.valueOf(c.get("id_campaign"))));
        map.forEach((k,v) -> {
            JSONObject campaignObj = campaign(v.get(0));
            JSONArray waves = new JSONArray();
            JSONArray members = new JSONArray();
            JSONArray scripts = new JSONArray();
            v.forEach(tuple -> {
                waves.put(wave(tuple));
                members.put(member(tuple));
                scripts.put(scripts(tuple, campaignDetails));
            });
            campaignObj.put("Waves", waves);
            campaignObj.put("Member", members);
            campaignObj.put("Scripts", scripts);
            campaigns.put(campaignObj);
        });
        return campaigns;
    }

    private JSONObject wave(Tuple campaign) {
        JSONObject wave = new JSONObject();
        wave.put("id_campaign_wave", campaign.get("id_campaign_wave"));
        wave.put("txt_campaign_wave_name", campaign.get("txt_campaign_wave_name"));
        wave.put("id_contact_list", campaign.get("id_contact_list"));
        wave.put("id_questionnaire", campaign.get("id_questionnaire"));
        wave.put("num_wave_response_reqd", campaign.get("num_wave_response_reqd"));
        wave.put("num_completed", campaign.get("num_completed"));
        wave.put("num_proofing", campaign.get("num_proofing"));
        wave.put("num_rejected", campaign.get("num_rejected"));
        wave.put("num_assigned", campaign.get("num_assigned"));
        wave.put("num_attempted", campaign.get("num_attempted"));
        wave.put("num_uniq_attempted", campaign.get("num_uniq_attempted"));
        wave.put("num_contacted", campaign.get("num_contacted"));
        wave.put("dat_wave_start", campaign.get("dat_wave_start"));
        wave.put("dat_wave_end", campaign.get("dat_wave_end"));
        wave.put("flg_test_wave", campaign.get("flg_test_wave"));
        return wave;
    }

    private JSONObject member(Tuple campaign) {
        JSONObject member = new JSONObject();
        member.put("cod_channel", campaign.get("cod_channel"));
        member.put("txt_campaign_role", campaign.get("txt_campaign_role"));
        member.put("flg_caller_role", campaign.get("flg_caller_role"));
        member.put("dat_joined_team", campaign.get("dat_joined_team"));
        member.put("dat_left_team", campaign.get("dat_left_team"));
        member.put("flg_curr_member", campaign.get("flg_curr_member"));
        member.put("num_contacts_capacity", campaign.get("num_contacts_capacity"));
        member.put("num_total_mthly_call_capacity", campaign.get("num_total_mthly_call_capacity"));
        member.put("num_mthly_valid_call_target", campaign.get("num_mthly_valid_call_target"));
        member.put("num_campaign_approval_pending", campaign.get("num_campaign_approval_pending"));
        member.put("num_questionnaire_approval_pending", campaign.get("num_questionnaire_approval_pending"));
        member.put("num_script_approval_pending", campaign.get("num_script_approval_pending"));
        member.put("num_flyer_approval_pending", campaign.get("num_flyer_approval_pending"));
        member.put("cod_preferred_lang", campaign.get("cod_preferred_lang"));
        return member;
    }

    private JSONObject scripts(Tuple tuple, List<Tuple> campaignDetails) {
        JSONObject script = new JSONObject();

        if (CollectionUtils.isEmpty(campaignDetails)) return script;

        List<Tuple> list = campaignDetails.stream().filter(detail ->
                String.valueOf(detail.get("id_campaign")).equals(String.valueOf(tuple.get("id_campaign")))
                && String.valueOf(detail.get("id_campaign_wave")).equals(String.valueOf(tuple.get("id_campaign_wave")))
                && String.valueOf(detail.get("cod_channel")).equalsIgnoreCase(String.valueOf(tuple.get("cod_channel")))
        ).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(list)) return script;

        list.forEach(s -> {
            script.put("id_campaign_wave", s.get("id_campaign_wave"));
            script.put("cod_channel", s.get("cod_channel"));
            script.put("cod_language", s.get("cod_language"));
            script.put("txt_intro_script", s.get("txt_intro_script"));
            script.put("txt_ack_script", s.get("txt_ack_script"));
            script.put("txt_not_qualified_script", s.get("txt_not_qualified_script"));
        });
        return script;
    }

    private JSONObject campaign(Tuple tuple) {
        JSONObject campaign = new JSONObject();
        campaign.put("id_campaign", tuple.get("id_campaign"));
        campaign.put("txt_campaign_short_code", tuple.get("txt_campaign_short_code"));
        campaign.put("txt_campaign_name", tuple.get("txt_campaign_name"));
        campaign.put("cod_campaign_type", tuple.get("cod_campaign_type"));
        campaign.put("txt_campaign_type_desc", tuple.get("txt_campaign_type_desc"));
        campaign.put("dat_start", tuple.get("dat_start"));
        campaign.put("dat_end", tuple.get("dat_end"));
        campaign.put("flg_campaign_active", tuple.get("flg_campaign_active"));
        campaign.put("cod_language", tuple.get("cod_language"));
        campaign.put("enu_campaign_status", tuple.get("enu_campaign_status"));
        campaign.put("flg_targeted_list", tuple.get("flg_targeted_list"));
        campaign.put("num_tot_response_reqd", tuple.get("num_tot_response_reqd"));
        campaign.put("num_attempts_per_item", tuple.get("num_attempts_per_item"));
        campaign.put("txt_camp_mgr_id", tuple.get("txt_camp_mgr_id"));
        campaign.put("txt_camp_mgr_name", tuple.get("txt_camp_mgr_name"));
        campaign.put("txt_created_by_id", tuple.get("txt_created_by_id"));
        campaign.put("txt_created_by_name", tuple.get("txt_created_by_name"));
        campaign.put("id_commissioned_by_thirdparty", tuple.get("id_commissioned_by_thirdparty"));
        campaign.put("txt_commisioned_by_name", tuple.get("txt_commisioned_by_name"));
        campaign.put("flg_voice_recording_consent", tuple.get("flg_voice_recording_consent"));
        campaign.put("flg_video_recording_consent", tuple.get("flg_video_recording_consent"));
        campaign.put("cod_promo", tuple.get("cod_promo"));
        campaign.put("txt_promo_name", tuple.get("txt_promo_name"));
        campaign.put("cod_product", tuple.get("cod_product"));
        campaign.put("txt_prod_name", tuple.get("txt_prod_name"));
        return campaign;
    }

}
