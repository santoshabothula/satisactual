package com.datawise.satisactual.entities.master;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mst_campaign_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CampaignTypeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private CampaignTypeEmbeddedKey id;

    @Column(name = "txt_campaign_type_desc")
    private String campaignTypeDesc;

    @Column(name = "flg_sales_campaign")
    private String salesCampaign;

    @Column(name = "flg_employee_survey")
    private String employeeSurvey;

    @Column(name = "num_min_size_for_reporting")
    private Integer minSizeForReporting;

    @Column(name = "flg_dnc_scrub_reqd")
    private String dncScrubReqd;

    @Column(name = "flg_structured")
    private String structured;

    @Column(name = "flg_response_reqd")
    private String responseReqd;

    @Column(name = "flg_targetlist_mandatory")
    private String targetListMandatory;

    @Column(name = "flg_default_value")
    private String defaultValue;

}