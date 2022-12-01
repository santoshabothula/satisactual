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

}
