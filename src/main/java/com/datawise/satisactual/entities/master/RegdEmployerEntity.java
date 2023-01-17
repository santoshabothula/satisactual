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
@Table(name = "mst_regd_employers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegdEmployerEntity extends MakerCheckerEntity {

    @EmbeddedId
    private RegdEmployerEmbeddedKey id;

    @Column(name = "cod_employer_category")
    private String employerCategory;
}