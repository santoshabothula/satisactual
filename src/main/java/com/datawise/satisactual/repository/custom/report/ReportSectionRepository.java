package com.datawise.satisactual.repository.custom.report;

import com.datawise.satisactual.entities.custom.report.ReportSectionEmbeddedKey;
import com.datawise.satisactual.entities.custom.report.ReportSectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportSectionRepository extends JpaRepository<ReportSectionEntity, ReportSectionEmbeddedKey> {
    // List<ReportSectionEntity> findByIdIdCampaignIdIdCampaignWaveIdTxtReportOwnerId(Long campaign, Long wave, String txtReportOwnerId);
}
