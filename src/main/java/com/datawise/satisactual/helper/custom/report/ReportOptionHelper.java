package com.datawise.satisactual.helper.custom.report;

import com.datawise.satisactual.entities.custom.report.ReportOptionEmbeddedKey;
import com.datawise.satisactual.entities.custom.report.ReportOptionEntity;
import com.datawise.satisactual.exception.SatisActualProcessException;
import com.datawise.satisactual.model.custom.report.ReportOptionsRequest;
import com.datawise.satisactual.repository.custom.report.ReportOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class ReportOptionHelper {

    @Autowired
    private ReportOptionRepository repository;

    public ReportOptionEmbeddedKey id(ReportOptionsRequest request) {
        return ReportOptionEmbeddedKey.builder()
                .txtReportOwnerId(CommonHelper.getLoggedInUser())
                .idCampaignWave(request.getIdCampaignWave())
                .idCampaign(request.getIdCampaign())
                .build();
    }

    public ReportOptionEntity reportOptions(ReportOptionsRequest request, String template) {
        return ReportOptionEntity.builder().
                id(id(request)).
                numLowRatingBelow(request.getNumLowRatingBelow()).
                numHighRatingAbove(request.getNumHighRatingAbove()).
                txtLastReportRunBy(CommonHelper.getLoggedInUser()).
                datTimeLastReportRun(LocalDateTime.now()).
                binReportOutputPath("").
                txtReportTitle(request.getTxtReportTitle()).
                txtReportAuthor(CommonHelper.getLoggedInUser()).
                txtReportDate(LocalDate.now()).
                txtReportSubtitle(request.getTxtReportSubtitle()).
                enuPageOrientation(request.getEnuPageOrientation()).
                codReportTemplate(template).
                build();
    }

    public void save(ReportOptionsRequest request, String template) {
        repository.save(reportOptions(request, template));
    }

    public ReportOptionsRequest get(Long campaign, Long wave, String ownerId, LocalDate startDate, LocalDate endDate) {
        return repository.findById(ReportOptionEmbeddedKey.builder().idCampaign(campaign).idCampaignWave(wave).txtReportOwnerId(ownerId).build()).
                map(e -> ReportOptionsRequest.builder()
                        .endDate(LocalDateTime.of(endDate, LocalTime.of(0, 0, 0)))
                        .startDate(LocalDateTime.of(startDate, LocalTime.of(0, 0, 0)))
                        .numLowRatingBelow(e.getNumLowRatingBelow())
                        .numHighRatingAbove(e.getNumHighRatingAbove())
                        .txtReportOwnerId(e.getTxtReportAuthor())
                        .idCampaignWave(e.getId().getIdCampaignWave())
                        .txtReportTitle(e.getTxtReportTitle())
                        .txtReportSubtitle(e.getTxtReportSubtitle())
                        .enuPageOrientation(e.getEnuPageOrientation())
                        .idCampaign(e.getId().getIdCampaign()).build()).orElseThrow();
    }
}
