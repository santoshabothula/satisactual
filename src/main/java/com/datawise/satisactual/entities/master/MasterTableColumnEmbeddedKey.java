package com.datawise.satisactual.entities.master;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MasterTableColumnEmbeddedKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "txt_table_name")
    private String tableName;

    @Column(name = "txt_column_name")
    private String columnName;

    @Column(name = "cod_rec_status")
    private String codRecStatus;
}
