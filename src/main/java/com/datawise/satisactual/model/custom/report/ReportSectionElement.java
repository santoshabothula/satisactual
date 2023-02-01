package com.datawise.satisactual.model.custom.report;

import com.datawise.satisactual.enums.AnalysisFormat;
import com.datawise.satisactual.enums.ChartType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ReportSectionElement {

    @NotNull
    @JsonProperty("num_section_sequence")
    private Integer num_section_sequence;

    @NotNull
    @JsonProperty("id_question")
    private Long idQuestion;

    @NotNull
    @JsonProperty("num_sequence")
    private Integer numSequence;

    @JsonProperty("enu_analysis_format")
    private AnalysisFormat enuAnalysisFormat;

    @JsonProperty("enu_chart_type")
    private ChartType enuChartType;

    @JsonProperty("id_crosstab_question")
    private Long idCrossTabQuestion;

    @JsonProperty("num_segment")
    private Integer numSegment;
}
