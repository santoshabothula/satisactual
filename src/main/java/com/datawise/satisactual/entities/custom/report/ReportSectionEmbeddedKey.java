package com.datawise.satisactual.entities.custom.report;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Data
@Builder
@Embeddable
public class ReportSectionEmbeddedKey implements Serializable {

    @Column(name = "ID_CAMPAIGN")
    private Long idCampaign;

    @Column(name = "ID_CAMPAIGN_WAVE")
    private Long idCampaignWave;

    @Column(name = "TXT_REPORT_OWNER_ID")
    private String txtReportOwnerId;

    @Column(name = "NUM_SECTION_SEQUENCE")
    private Integer numSectionSequence;
}
