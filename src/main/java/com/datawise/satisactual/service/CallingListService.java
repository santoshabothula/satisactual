package com.datawise.satisactual.service;

import com.datawise.satisactual.repository.StoredProcedureRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
public class CallingListService {

    @Autowired
    private StoredProcedureRepository storedProcedureRepository;

    public JSONArray getCallingList(String pTxtUserID) {
        JSONArray campaigns = new JSONArray();
        List<Tuple> rows = storedProcedureRepository.findCallingList(pTxtUserID);

        if (CollectionUtils.isEmpty(rows)) return campaigns;

        Map<String, List<Tuple>> map = rows.stream().collect(groupingBy(row -> String.valueOf(row.get("id_campaign"))));

        map.forEach((k,v) -> {
            JSONObject campaign = campaignInfo(v.get(0));
            JSONArray contactList = new JSONArray();
            v.forEach(tuple -> {
                JSONObject contact = contactInfo(tuple);
                contact.put("Item", itemDetails(tuple));
                contact.put("PersonalInfo", personalInfo(tuple));
                contactList.put(contact);
            });
            campaign.put("Calling List", contactList);
            campaigns.put(campaign);
        });
        return campaigns;
    }

    private JSONObject campaignInfo(Tuple tuple) {
        JSONObject campaignInfo = new JSONObject();
        campaignInfo.put("id_campaign", tuple.get("id_campaign"));
        campaignInfo.put("txt_campaign_name", tuple.get("txt_campaign_name"));
        campaignInfo.put("txt_campaign_short_code", tuple.get("txt_campaign_short_code"));
        campaignInfo.put("flg_voice_recording_consent", tuple.get("flg_voice_recording_consent"));
        campaignInfo.put("flg_video_recording_consent", tuple.get("flg_video_recording_consent"));
        campaignInfo.put("flg_nonconsent_terminate", tuple.get("flg_nonconsent_terminate"));
        campaignInfo.put("flg_anonymize_names", tuple.get("flg_anonymize_names"));
        campaignInfo.put("num_hours_between_attempts", tuple.get("num_hours_between_attempts"));
        campaignInfo.put("num_attempts_per_item", tuple.get("num_attempts_per_item"));
        campaignInfo.put("id_campaign_wave", tuple.get("id_campaign_wave"));
        campaignInfo.put("id_contact_list", tuple.get("id_contact_list"));
        campaignInfo.put("id_questionnaire", tuple.get("id_questionnaire"));
        campaignInfo.put("flg_test_wave", tuple.get("flg_test_wave"));
        campaignInfo.put("dat_wave_end", tuple.get("dat_wave_end"));
        return campaignInfo;
    }

    private JSONObject contactInfo(Tuple tuple) {
        JSONObject contactInfo = new JSONObject();
        contactInfo.put("num_list_item", tuple.get("num_list_item"));
        contactInfo.put("txt_assigned_to", tuple.get("txt_assigned_to"));
        contactInfo.put("dat_time_assigned", tuple.get("dat_time_assigned"));
        contactInfo.put("dat_scheduled_contact", tuple.get("dat_scheduled_contact"));
        contactInfo.put("txt_pref_time_to_call", tuple.get("txt_pref_time_to_call"));
        contactInfo.put("dat_do_not_call_after", tuple.get("dat_do_not_call_after"));
        contactInfo.put("txt_assigned_by", tuple.get("txt_assigned_by"));
        contactInfo.put("flg_do_not_call", tuple.get("flg_do_not_call"));
        contactInfo.put("txt_last_responder_name", tuple.get("txt_last_responder_name"));
        contactInfo.put("txt_last_phone_contacted", tuple.get("txt_last_phone_contacted"));
        contactInfo.put("cod_language", tuple.get("cod_language"));
        contactInfo.put("id_response", tuple.get("id_response"));
        contactInfo.put("txt_primary_phone", tuple.get("txt_primary_phone"));
        contactInfo.put("txt_alternative_phone", tuple.get("txt_alternative_phone"));
        contactInfo.put("txt_first_name", tuple.get("txt_first_name"));
        contactInfo.put("txt_last_name", tuple.get("txt_last_name"));
        contactInfo.put("txt_salutation", tuple.get("txt_salutation"));
        contactInfo.put("txt_addr_line1", tuple.get("txt_addr_line1"));
        contactInfo.put("txt_addr_line2", tuple.get("txt_addr_line2"));
        contactInfo.put("txt_addr_line3", tuple.get("txt_addr_line3"));
        contactInfo.put("txt_city", tuple.get("txt_city"));
        contactInfo.put("txt_pincode", tuple.get("txt_pincode"));
        contactInfo.put("txt_addr_country", tuple.get("txt_addr_country"));
        contactInfo.put("num_follow_up_level", tuple.get("num_follow_up_level"));
        contactInfo.put("num_hours_gmt_offset", tuple.get("num_hours_gmt_offset"));
        contactInfo.put("flg_max_attempts_completed", tuple.get("flg_max_attempts_completed"));
        contactInfo.put("dat_time_recent_attempt", tuple.get("dat_time_recent_attempt"));
        contactInfo.put("cod_recent_attempt_outcome", tuple.get("cod_recent_attempt_outcome"));
        contactInfo.put("cod_recent_attempt_sub_outcome", tuple.get("cod_recent_attempt_sub_outcome"));
        contactInfo.put("txt_recent_attempt_outcome", tuple.get("txt_recent_attempt_outcome"));
        contactInfo.put("txt_recent_attempt_sub_outcome", tuple.get("txt_recent_attempt_sub_outcome"));
        contactInfo.put("num_attempts", tuple.get("num_attempts"));
        contactInfo.put("txt_discussion_notes", tuple.get("txt_discussion_notes"));
        return contactInfo;
    }

    private JSONObject itemDetails(Tuple tuple) {
        JSONObject itemDetails = new JSONObject();
        itemDetails.put("txt_col_header_01", tuple.get("txt_col_header_01"));
        itemDetails.put("txt_col_header_02", tuple.get("txt_col_header_02"));
        itemDetails.put("txt_col_header_03", tuple.get("txt_col_header_03"));
        itemDetails.put("txt_col_header_04", tuple.get("txt_col_header_04"));
        itemDetails.put("txt_col_header_05", tuple.get("txt_col_header_05"));
        itemDetails.put("txt_col_header_06", tuple.get("txt_col_header_06"));
        itemDetails.put("txt_col_header_07", tuple.get("txt_col_header_07"));
        itemDetails.put("txt_col_header_08", tuple.get("txt_col_header_08"));
        itemDetails.put("txt_col_header_09", tuple.get("txt_col_header_09"));
        itemDetails.put("txt_col_header_10", tuple.get("txt_col_header_10"));
        itemDetails.put("txt_col_header_11", tuple.get("txt_col_header_11"));
        itemDetails.put("txt_col_header_12", tuple.get("txt_col_header_12"));
        itemDetails.put("txt_col_header_13", tuple.get("txt_col_header_13"));
        itemDetails.put("txt_col_header_14", tuple.get("txt_col_header_14"));
        itemDetails.put("txt_col_header_15", tuple.get("txt_col_header_15"));
        itemDetails.put("txt_col_header_16", tuple.get("txt_col_header_16"));
        itemDetails.put("txt_col_header_17", tuple.get("txt_col_header_17"));
        itemDetails.put("txt_col_header_18", tuple.get("txt_col_header_18"));
        itemDetails.put("txt_col_header_19", tuple.get("txt_col_header_19"));
        itemDetails.put("txt_col_header_20", tuple.get("txt_col_header_20"));
        itemDetails.put("txt_col_header_21", tuple.get("txt_col_header_21"));
        itemDetails.put("txt_col_header_22", tuple.get("txt_col_header_22"));
        itemDetails.put("txt_col_header_23", tuple.get("txt_col_header_23"));
        itemDetails.put("txt_col_header_24", tuple.get("txt_col_header_24"));
        itemDetails.put("txt_col_header_25", tuple.get("txt_col_header_25"));
        itemDetails.put("txt_col_header_26", tuple.get("txt_col_header_26"));
        itemDetails.put("txt_col_header_27", tuple.get("txt_col_header_27"));
        itemDetails.put("txt_col_header_28", tuple.get("txt_col_header_28"));
        itemDetails.put("txt_col_header_29", tuple.get("txt_col_header_29"));
        itemDetails.put("txt_col_header_30", tuple.get("txt_col_header_30"));
        itemDetails.put("enu_col_display_type_01", tuple.get("enu_col_display_type_01"));
        itemDetails.put("enu_col_display_type_02", tuple.get("enu_col_display_type_02"));
        itemDetails.put("enu_col_display_type_03", tuple.get("enu_col_display_type_03"));
        itemDetails.put("enu_col_display_type_04", tuple.get("enu_col_display_type_04"));
        itemDetails.put("enu_col_display_type_05", tuple.get("enu_col_display_type_05"));
        itemDetails.put("enu_col_display_type_06", tuple.get("enu_col_display_type_06"));
        itemDetails.put("enu_col_display_type_07", tuple.get("enu_col_display_type_07"));
        itemDetails.put("enu_col_display_type_08", tuple.get("enu_col_display_type_08"));
        itemDetails.put("enu_col_display_type_09", tuple.get("enu_col_display_type_09"));
        itemDetails.put("enu_col_display_type_10", tuple.get("enu_col_display_type_10"));
        itemDetails.put("enu_col_display_type_11", tuple.get("enu_col_display_type_11"));
        itemDetails.put("enu_col_display_type_12", tuple.get("enu_col_display_type_12"));
        itemDetails.put("enu_col_display_type_13", tuple.get("enu_col_display_type_13"));
        itemDetails.put("enu_col_display_type_14", tuple.get("enu_col_display_type_14"));
        itemDetails.put("enu_col_display_type_15", tuple.get("enu_col_display_type_15"));
        itemDetails.put("enu_col_display_type_16", tuple.get("enu_col_display_type_16"));
        itemDetails.put("enu_col_display_type_17", tuple.get("enu_col_display_type_17"));
        itemDetails.put("enu_col_display_type_18", tuple.get("enu_col_display_type_18"));
        itemDetails.put("enu_col_display_type_19", tuple.get("enu_col_display_type_19"));
        itemDetails.put("enu_col_display_type_20", tuple.get("enu_col_display_type_20"));
        itemDetails.put("enu_col_display_type_21", tuple.get("enu_col_display_type_21"));
        itemDetails.put("enu_col_display_type_22", tuple.get("enu_col_display_type_22"));
        itemDetails.put("enu_col_display_type_23", tuple.get("enu_col_display_type_23"));
        itemDetails.put("enu_col_display_type_24", tuple.get("enu_col_display_type_24"));
        itemDetails.put("enu_col_display_type_25", tuple.get("enu_col_display_type_25"));
        itemDetails.put("enu_col_display_type_26", tuple.get("enu_col_display_type_26"));
        itemDetails.put("enu_col_display_type_27", tuple.get("enu_col_display_type_27"));
        itemDetails.put("enu_col_display_type_28", tuple.get("enu_col_display_type_28"));
        itemDetails.put("enu_col_display_type_29", tuple.get("enu_col_display_type_29"));
        itemDetails.put("enu_col_display_type_30", tuple.get("enu_col_display_type_30"));
        itemDetails.put("flg_high_priority_01", tuple.get("flg_high_priority_01"));
        itemDetails.put("flg_high_priority_02", tuple.get("flg_high_priority_02"));
        itemDetails.put("flg_high_priority_03", tuple.get("flg_high_priority_03"));
        itemDetails.put("flg_high_priority_04", tuple.get("flg_high_priority_04"));
        itemDetails.put("flg_high_priority_05", tuple.get("flg_high_priority_05"));
        itemDetails.put("flg_high_priority_06", tuple.get("flg_high_priority_06"));
        itemDetails.put("flg_high_priority_07", tuple.get("flg_high_priority_07"));
        itemDetails.put("flg_high_priority_08", tuple.get("flg_high_priority_08"));
        itemDetails.put("flg_high_priority_09", tuple.get("flg_high_priority_09"));
        itemDetails.put("flg_high_priority_10", tuple.get("flg_high_priority_10"));
        itemDetails.put("flg_high_priority_11", tuple.get("flg_high_priority_11"));
        itemDetails.put("flg_high_priority_12", tuple.get("flg_high_priority_12"));
        itemDetails.put("flg_high_priority_13", tuple.get("flg_high_priority_13"));
        itemDetails.put("flg_high_priority_14", tuple.get("flg_high_priority_14"));
        itemDetails.put("flg_high_priority_15", tuple.get("flg_high_priority_15"));
        itemDetails.put("flg_high_priority_16", tuple.get("flg_high_priority_16"));
        itemDetails.put("flg_high_priority_17", tuple.get("flg_high_priority_17"));
        itemDetails.put("flg_high_priority_18", tuple.get("flg_high_priority_18"));
        itemDetails.put("flg_high_priority_19", tuple.get("flg_high_priority_19"));
        itemDetails.put("flg_high_priority_20", tuple.get("flg_high_priority_20"));
        itemDetails.put("flg_high_priority_21", tuple.get("flg_high_priority_21"));
        itemDetails.put("flg_high_priority_22", tuple.get("flg_high_priority_22"));
        itemDetails.put("flg_high_priority_23", tuple.get("flg_high_priority_23"));
        itemDetails.put("flg_high_priority_24", tuple.get("flg_high_priority_24"));
        itemDetails.put("flg_high_priority_25", tuple.get("flg_high_priority_25"));
        itemDetails.put("flg_high_priority_26", tuple.get("flg_high_priority_26"));
        itemDetails.put("flg_high_priority_27", tuple.get("flg_high_priority_27"));
        itemDetails.put("flg_high_priority_28", tuple.get("flg_high_priority_28"));
        itemDetails.put("flg_high_priority_29", tuple.get("flg_high_priority_29"));
        itemDetails.put("flg_high_priority_30", tuple.get("flg_high_priority_30"));
        itemDetails.put("txt_value_col_1", tuple.get("txt_value_col_1"));
        itemDetails.put("txt_value_col_2", tuple.get("txt_value_col_2"));
        itemDetails.put("txt_value_col_3", tuple.get("txt_value_col_3"));
        itemDetails.put("txt_value_col_4", tuple.get("txt_value_col_4"));
        itemDetails.put("txt_value_col_5", tuple.get("txt_value_col_5"));
        itemDetails.put("txt_value_col_6", tuple.get("txt_value_col_6"));
        itemDetails.put("txt_value_col_7", tuple.get("txt_value_col_7"));
        itemDetails.put("txt_value_col_8", tuple.get("txt_value_col_8"));
        itemDetails.put("txt_value_col_9", tuple.get("txt_value_col_9"));
        itemDetails.put("txt_value_col_10", tuple.get("txt_value_col_10"));
        itemDetails.put("txt_value_col_11", tuple.get("txt_value_col_11"));
        itemDetails.put("txt_value_col_12", tuple.get("txt_value_col_12"));
        itemDetails.put("txt_value_col_13", tuple.get("txt_value_col_13"));
        itemDetails.put("txt_value_col_14", tuple.get("txt_value_col_14"));
        itemDetails.put("txt_value_col_15", tuple.get("txt_value_col_15"));
        itemDetails.put("txt_value_col_16", tuple.get("txt_value_col_16"));
        itemDetails.put("txt_value_col_17", tuple.get("txt_value_col_17"));
        itemDetails.put("txt_value_col_18", tuple.get("txt_value_col_18"));
        itemDetails.put("txt_value_col_19", tuple.get("txt_value_col_19"));
        itemDetails.put("txt_value_col_20", tuple.get("txt_value_col_20"));
        itemDetails.put("txt_value_col_21", tuple.get("txt_value_col_21"));
        itemDetails.put("txt_value_col_22", tuple.get("txt_value_col_22"));
        itemDetails.put("txt_value_col_23", tuple.get("txt_value_col_23"));
        itemDetails.put("txt_value_col_24", tuple.get("txt_value_col_24"));
        itemDetails.put("txt_value_col_25", tuple.get("txt_value_col_25"));
        itemDetails.put("txt_value_col_26", tuple.get("txt_value_col_26"));
        itemDetails.put("txt_value_col_27", tuple.get("txt_value_col_27"));
        itemDetails.put("txt_value_col_28", tuple.get("txt_value_col_28"));
        itemDetails.put("txt_value_col_29", tuple.get("txt_value_col_29"));
        itemDetails.put("txt_value_col_30", tuple.get("txt_value_col_30"));
        return itemDetails;
    }

    private JSONObject personalInfo(Tuple tuple) {
        JSONObject personalInfo = new JSONObject();
        personalInfo.put("txt_uniq_customer_id", tuple.get("txt_uniq_customer_id"));
        personalInfo.put("txt_customer_segment", tuple.get("txt_customer_segment"));
        personalInfo.put("txt_recent_actvty_desc", tuple.get("txt_recent_actvty_desc"));
        personalInfo.put("txt_recent_actvty_date", tuple.get("txt_recent_actvty_date"));
        personalInfo.put("txt_recent_actvty_location", tuple.get("txt_recent_actvty_location"));
        personalInfo.put("txt_recent_actvty_channel", tuple.get("txt_recent_actvty_channel"));
        personalInfo.put("txt_recent_actvty_prod", tuple.get("txt_recent_actvty_prod"));
        personalInfo.put("txt_offer_prod", tuple.get("txt_offer_prod"));
        personalInfo.put("txt_offer_value", tuple.get("txt_offer_value"));
        personalInfo.put("txt_offer_valid_till", tuple.get("txt_offer_valid_till"));
        personalInfo.put("txt_offer_price", tuple.get("txt_offer_price"));
        personalInfo.put("txt_offer_discount", tuple.get("txt_offer_discount"));
        personalInfo.put("txt_email", tuple.get("txt_email"));
        personalInfo.put("txt_rel_manager_name", tuple.get("txt_rel_manager_name"));
        personalInfo.put("txt_rel_manager_email", tuple.get("txt_rel_manager_email"));
        personalInfo.put("txt_rel_manager_phone", tuple.get("txt_rel_manager_phone"));
        personalInfo.put("txt_preferred_lang", tuple.get("txt_preferred_lang"));
        personalInfo.put("txt_facebookid", tuple.get("txt_facebookid"));
        personalInfo.put("txt_twitterid", tuple.get("txt_twitterid"));
        personalInfo.put("txt_dnc", tuple.get("txt_dnc"));
        return personalInfo;
    }
}
