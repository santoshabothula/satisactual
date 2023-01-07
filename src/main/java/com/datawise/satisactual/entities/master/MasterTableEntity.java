package com.datawise.satisactual.entities.master;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sys_master_tables")
@Getter
@Setter
public class MasterTableEntity extends BaseEntity {

    @Id
    @Column(name = "txt_table_name")
    private String tableName;

    @Column(name = "txt_table_desc")
    private String tableDesc;

    @Column(name = "txt_code_col_name")
    private String codeColName;

    @Column(name = "txt_descr_col_name")
    private String descrColName;

    @Column(name = "txt_param_view_option")
    private String paramViewOption;

    @Column(name = "txt_param_add_program")
    private String paramAddProgram;

    @Column(name = "txt_param_mod_program")
    private String paramModProgram;

    @Column(name = "txt_param_del_program")
    private String paramDelOption;

    @Column(name = "txt_param_auth_program")
    private String paramAuthProgram;

    @Column(name = "flg_auto_auth")
    private String isAutoAuth;
}
