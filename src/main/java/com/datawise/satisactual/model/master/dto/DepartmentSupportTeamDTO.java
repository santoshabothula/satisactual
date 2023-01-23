package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotNull
	@JsonProperty("id_third_party")
    private Long idThirdParty;

    @NotNull
    @Size(min = 1, max = 48)
	@JsonProperty("txt_login_id")
    private String loginId;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
	@JsonProperty("dat_from")
    private LocalDate fromDate;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
	@JsonProperty("dat_to")
    private LocalDate toDate;

	@JsonProperty("flg_hr_admin")
    private FlagYesNo hrAdmin;

    @Size(min = 1, max = 48)
	@JsonProperty("txt_role")
    private String role;

}
