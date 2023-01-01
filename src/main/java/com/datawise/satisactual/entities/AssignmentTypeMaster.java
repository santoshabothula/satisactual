package com.datawise.satisactual.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mst_assignment_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentTypeMaster extends MakerChecker {

    @EmbeddedId
    private AssignmentTypeEmbeddedKey id;

    @Column(name = "txt_assignment_type_desc")
    private String assignmentTypeDesc;

    @Column(name = "flg_default_value")
    private String isDefaultValue;
}
