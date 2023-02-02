package com.datawise.satisactual.model.master.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MasterTableDTO {

    @NotBlank
    @JsonProperty("txt_table_name")
    private String tableName;

    @NotBlank
    @JsonProperty("cod_rec_status")
    private String codRecordStatus;

    @NotBlank
    @JsonProperty("txt_table_desc")
    private String tableDesc;

    @JsonProperty("txt_code_col_name")
    private String codeColumnName;

    @JsonProperty("txt_descr_col_name")
    private String descriptionColumnName;

    @JsonProperty("txt_param_view_option")
    private String paramViewOption;

    @JsonProperty("txt_param_add_program")
    private String paramAddProgram;

    @JsonProperty("txt_param_mod_program")
    private String paramModifyProgram;

    @JsonProperty("txt_param_del_program")
    private String paramDeleteProgram;

    @JsonProperty("txt_param_auth_program")
    private String paramAuthProgram;

    @NotBlank
    @JsonProperty("flg_auto_auth")
    private String isAutoAuth;

    @NotBlank
    @JsonProperty("txt_last_maker_id")
    private String lastMakerId;

    @JsonProperty("dat_last_maker")
    private String lastMakerDateTime;

    @JsonProperty("txt_last_checker_id")
    private String lastCheckerId;

    @JsonProperty("dat_last_checker")
    private String lastCheckerDateTime;

}
