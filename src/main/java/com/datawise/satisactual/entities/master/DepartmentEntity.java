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
@Table(name = "mst_departments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentEntity extends MakerCheckerEntity {

    @EmbeddedId
    private DepartmentEmbeddedKey id;

    @Column(name = "txt_department_name")
    private String departmentName;

    @Column(name = "txt_dept_head_id")
    private String deptHeadId;

    @Column(name = "txt_dept_deputy_id")
    private String deptDeputyId;

    @Column(name = "cod_parent_department")
    private String parentDepartment;

    @Column(name = "flg_default_value")
    private String defaultValue;
}