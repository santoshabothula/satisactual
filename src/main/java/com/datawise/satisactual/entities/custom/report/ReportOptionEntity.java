package com.datawise.satisactual.entities.custom.report;


import com.datawise.satisactual.enums.PageOrientation;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "CMP_REPORT_OPTIONS")
public class ReportOptionEntity {

    @EmbeddedId
    private ReportOptionEmbeddedKey id;

    @Column(name = "NUM_LOW_RATING_BELOW")
    private Integer numLowRatingBelow;

    @Column(name = "NUM_HIGH_RATING_ABOVE")
    private Integer numHighRatingAbove;

    @Column(name = "TXT_LAST_REPORT_RUN_BY")
    private String txtLastReportRunBy;

    @Column(name = "DAT_TIME_LAST_REPORT_RUN")
    private LocalDateTime datTimeLastReportRun;

    @Column(name = "BIN_REPORT_OUTPUT_PATH")
    private String binReportOutputPath;

    @Column(name = "TXT_REPORT_TITLE")
    private String txtReportTitle;

    @Column(name = "TXT_REPORT_AUTHOR")
    private String txtReportAuthor;

    @Column(name = "TXT_REPORT_DATE")
    private LocalDate txtReportDate;

    @Column(name = "TXT_REPORT_SUBTITLE")
    private String txtReportSubtitle;

    @Column(name = "ENU_PAGE_ORIENTATION")
    private PageOrientation enuPageOrientation;

    @Column(name = "COD_REPORT_TEMPLATE")
    private String codReportTemplate;
}
