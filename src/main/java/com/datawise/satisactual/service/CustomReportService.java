package com.datawise.satisactual.service;

import com.datawise.satisactual.entities.custom.report.ReportOptionEntity;
import com.datawise.satisactual.helper.custom.report.ReportOptionHelper;
import com.datawise.satisactual.helper.custom.report.ReportSectionHelper;
import com.datawise.satisactual.helper.custom.report.ReportSectionQuestionHelper;
import com.datawise.satisactual.model.custom.report.CustomReportRequest;
import com.datawise.satisactual.model.custom.report.ReportOptionsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CustomReportService {

    @Autowired
    private ReportOptionHelper reportOptionHelper;
    @Autowired
    private ReportSectionHelper reportSectionHelper;
    @Autowired
    private ReportSectionQuestionHelper reportSectionQuestionHelper;

    public void save(CustomReportRequest request) {
        reportOptionHelper.save(request.getReportOptionsRequest(), request.getCodReportTemplate());
        reportSectionHelper.save(request);
        reportSectionQuestionHelper.save(request);
    }

    public void get(Long campaign, Long wave, String ownerId, LocalDate startDate, LocalDate endDate) {
        CustomReportRequest.
                builder().
                reportOptionsRequest(reportOptionHelper.get(campaign, wave, ownerId, startDate, endDate)).
                reportSectionRequest(reportSectionHelper.get(campaign, wave, ownerId, startDate, endDate)).
                build();
    }

}
