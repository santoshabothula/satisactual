package com.datawise.satisactual.helper.custom.report;

import com.datawise.satisactual.entities.custom.report.ReportSectionEmbeddedKey;
import com.datawise.satisactual.entities.custom.report.ReportSectionEntity;
import com.datawise.satisactual.model.custom.report.CustomReportRequest;
import com.datawise.satisactual.model.custom.report.ReportSectionRequest;
import com.datawise.satisactual.repository.custom.report.ReportSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ReportSectionHelper {

    @Autowired
    private ReportSectionRepository repository;

    public ReportSectionEmbeddedKey id(CustomReportRequest request, Integer sequence) {
        return ReportSectionEmbeddedKey.builder()
                .idCampaign(request.getReportOptionsRequest().getIdCampaign())
                .idCampaignWave(request.getReportOptionsRequest().getIdCampaignWave())
                .txtReportOwnerId(CommonHelper.getLoggedInUser())
                .numSectionSequence(sequence).build();
    }

    public ReportSectionEntity section(CustomReportRequest request, ReportSectionRequest section) {
            return ReportSectionEntity.builder()
                    .txtSectionName(section.getTxtSectionName())
                    .id(id(request, section.getNumSectionSequence()))
                    .codReportTemplate(request.getCodReportTemplate())
                    .txtSectionIntroText(section.getTxtSectionIntroText())
                    .txtSectionConcludeText(section.getTxtSectionConcludeText())
                    .build();
    }

    public void save(CustomReportRequest request) {
        if (Objects.isNull(request.getReportSectionRequest())) return;
        request.getReportSectionRequest().forEach(section -> repository.save(section(request, section)));
    }

    public List<ReportSectionRequest> get(Long campaign, Long wave, String ownerId, LocalDate startDate, LocalDate endDate) {
        return null;/*repository.findByIdIdCampaignIdIdCampaignWaveIdTxtReportOwnerId(campaign, wave, ownerId).stream().map(e ->
                ReportSectionRequest.builder()
                        .txtSectionIntroText(e.getTxtSectionIntroText())
                        .txtSectionConcludeText(e.getTxtSectionConcludeText())
                        .txtSectionName(e.getTxtSectionName())
                        .numSectionSequence(e.getId().getNumSectionSequence())
                        .build()
        ).collect(Collectors.toList());*/
    }
}
