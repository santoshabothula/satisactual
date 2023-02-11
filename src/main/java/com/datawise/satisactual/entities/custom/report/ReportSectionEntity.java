package com.datawise.satisactual.entities.custom.report;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Builder
@Entity
@Table(name = "CMP_REPORT_SECTIONS")
public class ReportSectionEntity {

    @EmbeddedId
    private ReportSectionEmbeddedKey id;

    @Column(name = "TXT_SECTION_NAME")
    private String txtSectionName;

    @Column(name = "TXT_SECTION_INTRO_TEXT")
    private String txtSectionIntroText;

    @Column(name = "TXT_SECTION_CONCLUDE_TEXT")
    private String txtSectionConcludeText;

    @Column(name = "COD_REPORT_TEMPLATE")
    private String codReportTemplate;
}
