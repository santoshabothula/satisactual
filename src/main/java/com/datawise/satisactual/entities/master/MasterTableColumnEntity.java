package com.datawise.satisactual.entities.master;

import com.datawise.satisactual.enums.FlagYesNo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_master_table_cols")
@Getter
@Setter
public class MasterTableColumnEntity extends MakerCheckerEntity {

    @EmbeddedId
    private MasterTableColumnEmbeddedKey id;

    @Column(name = "txt_column_label")
    private String columnLabel;

    @Column(name = "num_display_order")
    private String displayOrder;

    @Column(name = "txt_column_datatype")
    private String columnDatatype;

    @Column(name = "flg_primary_key_component")
    private String isPrimaryKey;

    @Column(name = "flg_display_value")
    private String isDisplayValue;

    @Column(name = "flg_edit_allowed")
    private String isEditAllowed;

}
