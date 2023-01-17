package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_department")
    private String codDepartment;

	@JsonProperty("id_third_party")
    private Long idThirdParty;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_department_name")
    private String departmentName;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_dept_head_id")
    private String deptHeadId;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_dept_deputy_id")
    private String deptDeputyId;

    @Size(min = 1, max = 4)
	@JsonProperty("cod_parent_department")
    private String parentDepartment;

	@JsonProperty("flg_default_value")
    private FlagYesNo defaultValue;
}
