package com.datawise.satisactual.repository;

import com.datawise.satisactual.entities.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface StoredProcedureRepository extends JpaRepository<UserMaster, String> {

    @Query(value = "CALL sp_get_report_campaigns(:pin_txt_login_id);", nativeQuery = true)
    List<Tuple> findCampaign(@Param("pin_txt_login_id") String userId);

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
}
