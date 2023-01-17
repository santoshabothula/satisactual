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
@Table(name = "mst_custom_model_attribs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomModelAttribEntity extends MakerCheckerEntity {

    @EmbeddedId
    private CustomModelAttribEmbeddedKey id;

    @Column(name = "num_response_score")
    private Integer responseScore;

    @Column(name = "num_multiplier")
    private Float multiplier;

    @Column(name = "enu_exponential_factor")
    private String exponentialFactor;

    @Column(name = "enu_transform_factor")
    private String transformFactor;
}