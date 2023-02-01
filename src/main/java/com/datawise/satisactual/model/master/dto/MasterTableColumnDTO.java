package com.datawise.satisactual.model.master.dto;

import com.datawise.satisactual.enums.FlagYesNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MasterTableColumnDTO {

    @JsonProperty("txt_table_name")
    private String tableName;

    @JsonProperty("txt_column_name")
    private String columnName;

    @JsonProperty("cod_rec_status")
    private String codRecStatus;

    @JsonProperty("txt_column_label")
    private String columnLabel;

    @JsonProperty("num_display_order")
    private String displayOrder;

    @JsonProperty("txt_column_datatype")
    private String columnDatatype;

    @JsonProperty("flg_primary_key_component")
    private FlagYesNo isPrimaryKey;

    @JsonProperty("flg_display_value")
    private FlagYesNo isDisplayValue;

    @JsonProperty("flg_edit_allowed")
    private FlagYesNo isEditAllowed;
}
