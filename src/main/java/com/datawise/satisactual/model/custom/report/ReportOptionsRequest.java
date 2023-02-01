package com.datawise.satisactual.model.custom.report;

import com.datawise.satisactual.enums.PageOrientation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class ReportOptionsRequest {

    @NotNull
    @JsonProperty("id_campaign")
    private Long idCampaign;

    @NotNull
    @JsonProperty("id_campaign_wave")
    private Long idCampaignWave;

    @NotBlank
    @Size(min = 1, max = 48)
    @JsonProperty("txt_report_owner_id")
    private String txtReportOwnerId;

    @NotNull
    @JsonProperty("start_date")
    private LocalDateTime startDate;

    @NotNull
    @JsonProperty("end_date")
    private LocalDateTime endDate;

    @JsonProperty("num_low_rating_below")
    private Integer numLowRatingBelow;

    @JsonProperty("num_high_rating_above")
    private Integer numHighRatingAbove;

    @Size(min = 1, max = 255)
    @JsonProperty("txt_report_title")
    private String txtReportTitle;

    @Size(min = 1, max = 255)
    @JsonProperty("txt_report_subtitle")
    private String txtReportSubtitle;

    @JsonProperty("enu_page_orientation")
    private PageOrientation enuPageOrientation;
}
