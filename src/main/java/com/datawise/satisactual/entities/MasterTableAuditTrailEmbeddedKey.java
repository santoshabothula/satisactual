package com.datawise.satisactual.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MasterTableAuditTrailEmbeddedKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cod_master_code")
    private String codMasterCode;

    @Column(name = "txt_master_table_name")
    private String masterTableName;

    @Column(name = "dat_last_maker")
    private LocalDateTime lastMaker;
}
