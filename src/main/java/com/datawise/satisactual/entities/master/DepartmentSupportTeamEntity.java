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
@Table(name = "mst_department_support_team")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentSupportTeamEntity extends MakerCheckerEntity {

    @EmbeddedId
    private DepartmentSupportTeamEmbeddedKey id;

    @Column(name = "dat_from")
    private LocalDate fromDate;

    @Column(name = "dat_to")
    private LocalDate toDate;

    @Column(name = "flg_hr_admin")
    private String hrAdmin;

    @Column(name = "txt_role")
    private String role;
}