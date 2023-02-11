package com.datawise.satisactual.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "mst_master_table_audit_trail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MasterTableAuditTrailMaster {

    @EmbeddedId
    private MasterTableAuditTrailEmbeddedKey id;

    @Column(name = "txt_last_maker_id")
    private String lastMakerId;

    @Column(name = "txt_last_checker_id")
    private String lastCheckerId;

    @Column(name = "dat_last_checker")
    private LocalDateTime lastChecker;

    @Column(name = "cod_menu_option")
    private String menuOption;

    @Column(name = "txt_changed_values")
    private String changedValues;
}
