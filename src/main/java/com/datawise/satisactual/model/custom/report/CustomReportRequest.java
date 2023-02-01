package com.datawise.satisactual.model.custom.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CustomReportRequest {

    @NotBlank
    @Size(min = 1, max = 4)
    private String cod_report_template;

    @Valid
    @JsonProperty("report-options")
    private ReportOptionsRequest reportOptionsRequest;

    @Valid
    @JsonProperty("report_sections")
    private List<ReportSectionRequest> reportSectionRequest;
}
