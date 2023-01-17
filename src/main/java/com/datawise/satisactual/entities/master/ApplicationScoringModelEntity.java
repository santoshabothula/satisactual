package com.datawise.satisactual.entities.master;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "mst_appl_scoring_models")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationScoringModelEntity extends MakerCheckerEntity {

    @EmbeddedId
    private ApplicationScoringModelEmbeddedKey id;

    @Column(name = "dat_valid_to")
    private LocalDate validTo;

    @Column(name = "txt_scoring_model_name")
    private String scoringModelName;

    @Column(name = "num_min_inc_score")
    private Integer minIncScore;

    @Column(name = "num_inc_score_weight")
    private Integer incScoreWeight;

    @Column(name = "num_min_coll_score")
    private Integer minCollScore;

    @Column(name = "num_coll_score_weight")
    private Integer collScoreWeight;

    @Column(name = "num_min_bureau_score")
    private Integer minBureauScore;

    @Column(name = "num_min_relation_score")
    private Integer minRelationScore;

    @Column(name = "num_relation_score_weight")
    private Integer relationScoreWeight;

    @Column(name = "num_min_guarantor_score")
    private Integer minGuarantorScore;

    @Column(name = "num_guarantor_score_weight")
    private Integer guarantorScoreWeight;

    @Column(name = "num_min_insurance_score")
    private Integer minInsuranceScore;

    @Column(name = "num_insurance_score_weight")
    private Integer insuranceScoreWeight;

    @Column(name = "num_min_debt_score")
    private Integer minDebtScore;

    @Column(name = "num_debt_score_weight")
    private Integer debtScoreWeight;

    @Column(name = "num_intercept")
    private Integer intercept;
}
