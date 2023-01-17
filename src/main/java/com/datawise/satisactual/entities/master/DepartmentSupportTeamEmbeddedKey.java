package com.datawise.satisactual.entities.master;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentSupportTeamEmbeddedKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cod_department")
    private String codDepartment;

    @Column(name = "id_third_party")
    private Long idThirdParty;

    @Column(name = "txt_login_id")
    private String loginId;

    @Column(name = "cod_rec_status")
    private String codRecordStatus;
}