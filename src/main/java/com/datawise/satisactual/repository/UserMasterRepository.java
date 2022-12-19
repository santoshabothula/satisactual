package com.datawise.satisactual.repository;

import com.datawise.satisactual.entities.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.util.List;

public interface UserMasterRepository extends JpaRepository<UserMaster, String> {

    @Query(name = "campaigns.details", nativeQuery = true)
    List<Tuple> findCampaignsDetails(@Param("txt_login_id") String txtLoginId, @Param("date") String date);

    @Query(name = "cmp.questionnaire.question-links", nativeQuery = true)
    List<Tuple> findQuestionnaireQuestionLinks(@Param("id_questionnaire") Integer idQuestionnaire);

    @Query(name = "cmp.questionnaire.translations", nativeQuery = true)
    List<Tuple> findQuestionnaireTranslations(@Param("id_questionnaire") Integer idQuestionnaire);

    @Query(name = "cmp.questionnaire.trans.options", nativeQuery = true)
    List<Tuple> findQuestionnaireTransOptions(@Param("id_questionnaire") Integer idQuestionnaire);

    @Query(value = "select id_questionnaire from cmp_questionnaire where id_questionnaire = :id_questionnaire and cod_rec_status = 'A'", nativeQuery = true)
    Integer findIdQuestionnaire(@Param("id_questionnaire") String idQuestionnaire);

    @Query(name = "field.survey", nativeQuery = true)
    List<Tuple> findFieldSurvey(@Param("id_campaign") String idCampaign, @Param("id_campaign_wave") String idCampaignWave, @Param("id_response") String idResponse);
}
