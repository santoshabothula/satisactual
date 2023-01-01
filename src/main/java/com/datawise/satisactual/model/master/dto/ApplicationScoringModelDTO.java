package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationScoringModelDTO extends MakerCheckerDTO {

    @NotBlank
    @Size(min = 1, max = 4)
    @JsonProperty("cod_scoring_model")
    private String codScoringModel;

    @NotBlank
    @Size(min = 1, max = 1)
    @JsonProperty("cod_rec_status")
    private String codRecordStatus;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    @JsonProperty("dat_valid_from")
    private LocalDate validFrom;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    @JsonProperty("dat_valid_to")
    private LocalDate validTo;

    @NotBlank
    @Size(min = 1, max = 24)
    @JsonProperty("txt_scoring_model_name")
    private String scoringModelName;

    @NotNull
    @JsonProperty("num_min_inc_score")
    private Integer minIncScore;

    @NotNull
    @JsonProperty("num_inc_score_weight")
    private Integer incScoreWeight;

    @NotNull
    @JsonProperty("num_min_coll_score")
    private Integer minCollScore;

    @NotNull
    @JsonProperty("num_coll_score_weight")
    private Integer collScoreWeight;

    @NotNull
    @JsonProperty("num_min_bureau_score")
    private Integer minBureauScore;

    @NotNull
    @JsonProperty("num_min_relation_score")
    private Integer minRelationScore;

    @NotNull
    @JsonProperty("num_relation_score_weight")
    private Integer relationScoreWeight;

    @NotNull
    @JsonProperty("num_min_guarantor_score")
    private Integer minGuarantorScore;

    @NotNull
    @JsonProperty("num_guarantor_score_weight")
    private Integer guarantorScoreWeight;

    @NotNull
    @JsonProperty("num_min_insurance_score")
    private Integer minInsuranceScore;

    @NotNull
    @JsonProperty("num_insurance_score_weight")
    private Integer insuranceScoreWeight;

    @NotNull
    @JsonProperty("num_min_debt_score")
    private Integer minDebtScore;

    @NotNull
    @JsonProperty("num_debt_score_weight")
    private Integer debtScoreWeight;

    @NotNull
    @JsonProperty("num_intercept")
    private Integer intercept;
}
