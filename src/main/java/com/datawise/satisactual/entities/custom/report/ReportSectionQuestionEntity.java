package com.datawise.satisactual.entities.custom.report;

import com.datawise.satisactual.enums.ChartType;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Builder
@Entity
@Table(name = "CMP_REPORT_SECTION_QUESTIONS")
public class ReportSectionQuestionEntity {

    @EmbeddedId
    private ReportSectionQuestionEmbeddedKey id;

    @Column(name = "NUM_SEQUENCE")
    private Integer numSequence;

    @Column(name = "ENU_CHART_TYPE")
    private ChartType enuChartType;

    @Column(name = "ID_CROSSTAB_QUESTION")
    private Long idCrossTabQuestion;

    @Column(name = "NUM_SEGMENT")
    private Integer numSegment;
}
