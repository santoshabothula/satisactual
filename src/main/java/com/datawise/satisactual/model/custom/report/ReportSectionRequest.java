package com.datawise.satisactual.model.custom.report;

import com.datawise.satisactual.model.custom.report.ReportSectionElement;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ReportSectionRequest {

    @NotNull
    @JsonProperty("page-number")
    private Integer pageNumber;

    @NotNull
    @JsonProperty("num_section_sequence")
    private Integer numSectionSequence;

    @NotBlank
    @Size(min = 1, max = 48)
    @JsonProperty("txt_section_name")
    private String txtSectionName;

    @JsonProperty("txt_section_intro_text")
    private String txtSectionIntroText;

    @JsonProperty("txt_section_conclude_text")
    private String txtSectionConcludeText;

    @JsonProperty("report_section_questions")
    private List<ReportSectionElement> reportSectionElement;
}
