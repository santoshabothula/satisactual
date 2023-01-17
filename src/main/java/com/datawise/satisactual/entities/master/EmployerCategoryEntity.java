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
@Table(name = "mst_employer_categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployerCategoryEntity extends MakerCheckerEntity {

    @EmbeddedId
    private EmployerCategoryEmbeddedKey id;

    @Column(name = "txt_employer_category_desc")
    private String employerCategoryDesc;

    @Column(name = "flg_default_value")
    private String defaultValue;

}