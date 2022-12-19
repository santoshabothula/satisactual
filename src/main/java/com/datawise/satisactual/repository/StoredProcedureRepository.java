package com.datawise.satisactual.repository;

import com.datawise.satisactual.entities.UserMaster;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface StoredProcedureRepository extends JpaRepository<UserMaster, String> {

    @Query(value = "CALL sp_get_report_campaigns(:pin_txt_login_id);", nativeQuery = true)
    List<Tuple> findCampaign(@Param("pin_txt_login_id") String userId);

    @Query(value = "CALL sp_get_my_campaigns(:pin_txt_login_id);", nativeQuery = true)
    List<Tuple> findMyCampaign(@Param("pin_txt_login_id") String userId);

    @Query(value = "CALL sp_get_campaign_waves(:pin_id_campaign);", nativeQuery = true)
    List<Tuple> findWaves(@Param("pin_id_campaign") String campaignId);

    @Query(value = "CALL sp_get_report_notes( :pin_id_campaign , :pin_id_campaign_wave);", nativeQuery = true)
    List<Tuple> findReportsNotes(@Param ("pin_id_campaign") String campaignId, @Param ("pin_id_campaign_wave") String campaignWaveId);

    @Query(value = "CALL sp_ins_upd_report_notes(:pin_id_campaign, :pin_id_campaign_wave, :pin_id_question, :pin_txt_section_name,:pin_txt_author_id, :pin_txt_note, :pin_dat_action_due,:pin_flg_include_in_report ,:pin_flg_resolved,:pin_txt_resolution_note)",nativeQuery = true)
    void updateReportNotes(
            @Param ("pin_id_campaign") Integer campaignId,
            @Param ("pin_id_campaign_wave") Integer campaignWaveId,
            @Param ("pin_id_question") Integer questionId,
            @Param ("pin_txt_section_name") String sectionName,
            @Param ("pin_txt_author_id") String authorId,
            @Param ("pin_txt_note") String note,
            @Param ("pin_dat_action_due") String noteDataActionDue,
            @Param ("pin_flg_include_in_report") String includeInReport,
            @Param ("pin_txt_resolution_note") String resolutionNote
    );

    @Query(value = "call sp_get_my_questionnaires(:pin_txt_login_id, 'Y');", nativeQuery = true)
    Tuple findQuestionnaires(@Param("pin_txt_login_id") String userId);

    @Query(value = "call sp_get_questionnaire (null, null, :questionnaireId, :lang, null);", nativeQuery = true)
    List<Tuple> findQuestionnaireByLang(Integer questionnaireId, String lang);

    @Query(value = "call sp_get_calling_list (:pin_txt_login_id);", nativeQuery = true)
    List<Tuple> findCallingList(@Param("pin_txt_login_id") String userId);

    @Query(value = "call sp_ins_field_survey (" +
            ":pin_id_campaign, " +
            ":pin_id_campaign_wave, " +
            ":pin_id_response, " +
            ":pin_num_contact_sequence, " +
            ":pin_id_question, " +
            ":pin_cod_option_selected, " +
            ":pin_txt_question_response, " +
            ":pin_cod_language, " +
            ":pin_dat_time_survey_start, " +
            ":pin_num_latitude_start, " +
            ":pin_num_longitude_start, " +
            ":pin_dat_time_survey_end, " +
            ":pin_num_latitude_end, " +
            ":pin_num_longitude_end, " +
            ":pin_id_contact_list, " +
            ":pin_num_list_item, " +
            ":pin_txt_survey_conducted_by, " +
            ":pin_txt_device_id, " +
            ":pin_txt_responder_name, " +
            ":pin_num_responder_age, " +
            ":pin_enu_responder_gender, " +
            ":pin_flg_anonymous_response, " +
            ":pin_flg_allow_clarif_contact, " +
            ":pin_bin_recording_path)", nativeQuery = true)
    void submitFiledSurveyList(
            @Param("pin_id_campaign") String pinIdCampaign,
            @Param("pin_id_campaign_wave") String pinIdCampaignWave,
            @Param("pin_id_response") String pinIdResponse,
            @Param("pin_num_contact_sequence") String pinNumContactSequence,
            @Param("pin_id_question") String pinIdQuestion,
            @Param("pin_cod_option_selected") String pinCodOptionSelected,
            @Param("pin_txt_question_response") String pinTxtQuestionResponse,
            @Param("pin_cod_language") String pinCodLanguage,
            @Param("pin_dat_time_survey_start") String pinDatTimeSurveyStart,
            @Param("pin_num_latitude_start") String pinNumLatitudeStart,
            @Param("pin_num_longitude_start") String pinNumLongitudeStart,
            @Param("pin_dat_time_survey_end") String pinDatTimeSurveyEnd,
            @Param("pin_num_latitude_end") String pinNumLatitudeEnd,
            @Param("pin_num_longitude_end") String pinNumLongitudeEnd,
            @Param("pin_id_contact_list") String pinIdContactList,
            @Param("pin_num_list_item") String pinNumListItem,
            @Param("pin_txt_survey_conducted_by") String pinTxtSurveyConductedBy,
            @Param("pin_txt_device_id") String pinTxtDeviceId,
            @Param("pin_txt_responder_name") String pinTxtResponderName,
            @Param("pin_num_responder_age") String pinNumResponderAge,
            @Param("pin_enu_responder_gender") String pinEnuResponderGender,
            @Param("pin_flg_anonymous_response") String pinFlgAnonymousResponse,
            @Param("pin_flg_allow_clarif_contact") String pinFlgAllowCLArIFContact,
            @Param("pin_bin_recording_path") String pinBinRecordingPath
    );

    @Query(value = "call sp_ins_response_detail (:pin_id_campaign, :pin_id_campaign_wave, :pin_id_response, :pin_id_question, :pin_cod_option_selected, :pin_txt_question_response, :pin_num_rank, :pin_bin_recording_path)", nativeQuery = true)
    void submitResponseDetails(
            @Param("pin_id_campaign") Integer pinIdCampaign,
            @Param("pin_id_campaign_wave") Integer pinIdCampaignWave,
            @Param("pin_id_response") Integer pinIdResponse,
            @Param("pin_id_question") Long pinIdQuestion,
            @Param("pin_cod_option_selected") String pinCodOptionSelected,
            @Param("pin_txt_question_response") String pinTxtQuestionResponse,
            @Param("pin_num_rank") Integer pinNumRank,
            @Param("pin_bin_recording_path") String pinBinRecordingPath
    );
}
