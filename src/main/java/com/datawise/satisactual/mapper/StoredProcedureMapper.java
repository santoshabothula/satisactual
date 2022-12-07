package com.datawise.satisactual.mapper;

import com.datawise.satisactual.model.CampaignDetails;
import com.datawise.satisactual.model.ReportNoteDetails;
import com.datawise.satisactual.model.WaveDetails;
import org.springframework.util.CollectionUtils;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class StoredProcedureMapper {

    public static List<CampaignDetails> mapCampaigns(List<Tuple> rows) {
        List<CampaignDetails> campaignDetails = new ArrayList<>();
        if (CollectionUtils.isEmpty(rows)) return campaignDetails;
        return rows.stream().map(StoredProcedureMapper::mapCampaign).collect(Collectors.toList());
    }

    public static CampaignDetails mapCampaign(Tuple row) {
        CampaignDetails campaignDetails = new CampaignDetails();
        campaignDetails.setTxtCampaignName(convertToString.apply(row,"txt_campaign_name"));
        campaignDetails.setTxtCampaignTypeDesc(convertToString.apply(row,"txt_campaign_type_desc"));
        campaignDetails.setIdCampaign(convertToBigInteger.apply(row,"id_campaign"));
        campaignDetails.setCodCampaignType(convertToString.apply(row,"cod_campaign_type"));
        campaignDetails.setTxtCampaignShortCode(convertToString.apply(row,"txt_campaign_short_code"));
        campaignDetails.setFlgCurrentCampaign(convertToString.apply(row,"flg_current_campaign"));
        campaignDetails.setDatStart(convertToDate.apply(row,"dat_start"));
        campaignDetails.setDatEnd(convertToDate.apply(row,"dat_end"));
        campaignDetails.setTxtCampaignTitle(convertToString.apply(row,"txt_campaign_title"));
        campaignDetails.setTxtCampaignSubtitle(convertToString.apply(row,"txt_campaign_subtitle"));
        campaignDetails.setEnuCampaignStatus(convertToString.apply(row,"enu_campaign_status"));
        campaignDetails.setTxtCampMgrId(convertToString.apply(row,"txt_camp_mgr_id"));
        campaignDetails.setTxtCampMgrName(convertToString.apply(row,"txt_camp_mgr_name"));
        campaignDetails.setIdCommissionedByThirdParty(convertToBigInteger.apply(row,"id_commissioned_by_thirdparty"));
        campaignDetails.setTxtThirdPartyName(convertToString.apply(row,"txt_third_party_name"));
        campaignDetails.setTxtThirdPartyShortName(convertToString.apply(row,"txt_third_party_short_name"));
        campaignDetails.setBinLogoToDisplay(convertToString.apply(row,"bin_logo_to_display"));
        return campaignDetails;
    }

    public static List<WaveDetails> mapWaves(List<Tuple> rows) {
        List<WaveDetails> waveDetails = new ArrayList<>();
        if (CollectionUtils.isEmpty(rows)) return waveDetails;
        return rows.stream().map(StoredProcedureMapper::mapWave).collect(Collectors.toList());
    }

    public static WaveDetails mapWave(Tuple row) {
        WaveDetails waveDetails = new WaveDetails();
        waveDetails.setIdCampaign(convertToBigInteger.apply(row, "id_campaign"));
        waveDetails.setIdCampaignWave(convertToBigInteger.apply(row,"id_campaign_wave"));
        waveDetails.setTxtCampaignWaveName(convertToString.apply(row,"txt_campaign_wave_name"));
        waveDetails.setIdContactList(convertToBigInteger.apply(row,"id_contact_list"));
        waveDetails.setTxtContactListName(convertToString.apply(row,"txt_contact_list_name"));
        waveDetails.setDatWaveStart(convertToDate.apply(row,"dat_wave_start"));
        waveDetails.setDatWaveEnd(convertToDate.apply(row,"dat_wave_end"));
        waveDetails.setFlgCurrentWave(convertToString.apply(row,"flg_current_wave"));
        waveDetails.setFlgFutureWave(convertToString.apply(row,"flg_future_wave"));
        waveDetails.setFlgExpiredWave(convertToString.apply(row,"flg_expired_wave"));
        waveDetails.setTxtWaveDeliveryHeadId(convertToString.apply(row,"txt_wave_delivery_head_id"));
        waveDetails.setTxtWaveRequisitionerId(convertToString.apply(row,"txt_wave_requisitioner_id"));
        waveDetails.setIdQuestionnaire(convertToBigInteger.apply(row,"id_questionnaire"));
        waveDetails.setTxtQuestionnaireName(convertToString.apply(row,"txt_questionnaire_name"));
        waveDetails.setFlgAssessment((convertToString.apply(row,"flg_assessment")));
        waveDetails.setTxtPrintFormatFile(convertToString.apply(row,"txt_print_format_file"));
        waveDetails.setNumTotResponseReq(convertToInteger.apply(row,"num_tot_response_reqd"));
        waveDetails.setNumEscalationLevels(convertToShort.apply(row,"num_escalation_levels"));
        waveDetails.setNumHoursToEscalation(convertToShort.apply(row,"num_hours_to_escalation"));
        waveDetails.setNumLowRatingBelow(convertToShort.apply(row,"num_low_rating_below"));
        waveDetails.setNumHighRatingAbove(convertToShort.apply(row,"num_high_rating_above"));
        waveDetails.setCodCssTheme((convertToString.apply(row,"cod_css_theme")));
        waveDetails.setFlgTestWave((convertToString.apply(row,"flg_test_wave")));
        return waveDetails;
    }

    public static List<ReportNoteDetails> mapReportNote(List<Tuple> row) {
        List<ReportNoteDetails> reportNoteDetails = new ArrayList<>();
        if(CollectionUtils.isEmpty(row)) return reportNoteDetails;
        return row.stream().map(StoredProcedureMapper::mapReportNote).collect(Collectors.toList());
    }

    public static ReportNoteDetails mapReportNote(Tuple row) {
        ReportNoteDetails reportnotedetails = new ReportNoteDetails();
        reportnotedetails.setIdQuestion(convertToBigInteger.apply(row,"id_question"));
        reportnotedetails.setTxtSectionName(convertToString.apply(row,"txt_section_name"));
        reportnotedetails.setTxtAuthorId(convertToString.apply(row,"txt_author_id"));
        reportnotedetails.setTxtNote(convertToString.apply(row,"txt_note"));
        reportnotedetails.setDatActionDue(convertToString.apply(row,"dat_action_due"));
        reportnotedetails.setFlgIncludeInReport(convertToString.apply(row,"flg_include_in_report"));
        reportnotedetails.setFlgResolved(convertToString.apply(row,"flg_include_in_report"));
        reportnotedetails.setDatTimeResolved(convertToDate.apply(row,"dat_time_resolved"));
        reportnotedetails.setTxtResolutionNote(convertToString.apply(row,"txt_resolution_note"));
        reportnotedetails.setDatTimeAdded(convertToDate.apply(row,"dat_time_added"));
        reportnotedetails.setDatTimeLastEdited(convertToDate.apply(row,"dat_time_last_edited"));
        reportnotedetails.setNumQuestionPage(convertToInteger.apply(row, "num_question_page"));
        reportnotedetails.setNumQuestionSequence(convertToInteger.apply(row,"num_question_sequence"));

        return reportnotedetails;
    }

    static BiFunction<Tuple, String, BigInteger> convertToBigInteger = (row, key) -> Objects.nonNull(row.get(key)) ? BigInteger.valueOf(Long.parseLong(String.valueOf(row.get(key)))) : null;

    static BiFunction<Tuple, String, Integer> convertToInteger = (row, key) -> Objects.nonNull(row.get(key)) ? Integer.valueOf(String.valueOf(row.get(key))) : null;

    static BiFunction<Tuple, String, Short> convertToShort = (row, key) -> Objects.nonNull(row.get(key)) ? Short.valueOf(String.valueOf(row.get(key))) : null;

    static BiFunction<Tuple, String, String> convertToString = (row, key) -> Objects.nonNull(row.get(key)) ? String.valueOf(row.get(key)) : null;

    static BiFunction<Tuple, String, Date> convertToDate = (row, key) -> Objects.nonNull(row.get(key)) ? Objects.isNull(row.get(key)) ? null : (Date) row.get(key) : null;
}
