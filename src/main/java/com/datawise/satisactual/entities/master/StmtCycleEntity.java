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
@Table(name = "mst_stmt_cycles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StmtCycleEntity extends MakerCheckerEntity {

    @EmbeddedId
    private StmtCycleEmbeddedKey id;

    @Column(name = "txt_stmt_cycle_desc")
    private String stmtCycleDesc;

    @Column(name = "num_day_of_mth")
    private Integer dayOfMth;

    @Column(name = "num_days_stmt_lead_time")
    private Integer daysStmtLeadTime;

    @Column(name = "dat_last_cycle_processed")
    private LocalDate lastCycleProcessed;

    @Column(name = "flg_default_value")
    private String defaultValue;
}