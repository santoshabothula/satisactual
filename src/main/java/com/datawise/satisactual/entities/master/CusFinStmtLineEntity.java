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
@Table(name = "mst_cus_fin_stmt_lines")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CusFinStmtLineEntity extends MakerCheckerEntity {

    @EmbeddedId
    private CusFinStmtLineEmbeddedKey id;

    @Column(name = "txt_fin_stmt_line_name")
    private String finStmtLineName;

    @Column(name = "enu_line_type")
    private String lineType;

    @Column(name = "num_line_seq")
    private Integer lineSeq;
}