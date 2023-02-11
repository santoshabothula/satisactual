package com.datawise.satisactual.model.custom.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
public class CustomReportRequest {

    @NotBlank
    @Size(min = 1, max = 4)
    @JsonProperty("cod_report_template")
    private String codReportTemplate;

    @Valid
    @JsonProperty("report-options")
    private ReportOptionsRequest reportOptionsRequest;

    @Valid
    @JsonProperty("report_sections")
    private List<ReportSectionRequest> reportSectionRequest;
}
