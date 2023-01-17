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
@Table(name = "mst_bank_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankTypeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private BankTypeEmbeddedKey id;

    @Column(name = "txt_bank_type_desc")
    private String bankTypeDesc;

    @Column(name = "flg_default_value")
    private String defaultValue;
}
