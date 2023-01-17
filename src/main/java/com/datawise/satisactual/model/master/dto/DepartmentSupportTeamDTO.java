package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentSupportTeamDTO extends BaseDTO {

    @NotBlank
    @Size(min = 1, max = 4)
	@JsonProperty("cod_department")
    private String codDepartment;

	@JsonProperty("id_third_party")
    private Long idThirdParty;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_login_id")
    private String loginId;

	@JsonProperty("dat_from")
    private LocalDate fromDate;

	@JsonProperty("dat_to")
    private LocalDate toDate;

	@JsonProperty("flg_hr_admin")
    private FlagYesNo hrAdmin;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_role")
    private String role;

}
