package com.datawise.satisactual.helper.custom.report;

import com.datawise.satisactual.entities.custom.report.ReportSectionQuestionEmbeddedKey;
import com.datawise.satisactual.entities.custom.report.ReportSectionQuestionEntity;
import com.datawise.satisactual.model.custom.report.CustomReportRequest;
import com.datawise.satisactual.model.custom.report.ReportSectionElement;
import com.datawise.satisactual.repository.custom.report.ReportSectionQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ReportSectionQuestionHelper {

    @Autowired
    private ReportSectionQuestionRepository repository;

    public ReportSectionQuestionEmbeddedKey id(CustomReportRequest request, ReportSectionElement element) {
        return ReportSectionQuestionEmbeddedKey.builder()
                .idCampaign(request.getReportOptionsRequest().getIdCampaign())
                .idCampaignWave(request.getReportOptionsRequest().getIdCampaignWave())
                .txtReportOwnerId(CommonHelper.getLoggedInUser())
                .enuAnalysisFormat(element.getEnuAnalysisFormat())
                .idQuestion(element.getIdQuestion())
                .numSectionSequence(element.getNumSectionSequence()).build();
    }

    public ReportSectionQuestionEntity sectionElement(CustomReportRequest request, ReportSectionElement element) {
        return ReportSectionQuestionEntity.builder()
                .id(id(request, element))
                .idCrossTabQuestion(element.getIdCrossTabQuestion())
                .enuChartType(element.getEnuChartType())
                .numSequence(element.getNumSequence())
                .numSegment(element.getNumSegment())
                .enuChartType(element.getEnuChartType()).build();
    }

    public void save(ReportSectionQuestionEntity entity) {
        repository.save(entity);
    }

    public void save(CustomReportRequest request) {
        if (Objects.isNull(request.getReportOptionsRequest()) || Objects.isNull(request.getReportSectionRequest())) return;
        request.getReportSectionRequest().forEach(section -> section.getReportSectionElement().forEach(element -> repository.save(sectionElement(request, element))));
    }
}
