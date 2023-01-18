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
@Table(name = "mst_bank_codes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankCodeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private BankCodeEmbeddedKey id;

    @Column(name = "txt_bank_short_name")
    private String bankShortName;

    @Column(name = "txt_bank_name")
    private String bankName;

    @Column(name = "cod_bank_type")
    private String bankType;

    @Column(name = "cod_owner_bank")
    private String ownerBank;

    @Column(name = "cod_owner_country")
    private String ownerCountry;

    @Column(name = "dat_opened")
    private LocalDate opened;

    @Column(name = "dat_closed_or_merged")
    private LocalDate closedOrMerged;

    @Column(name = "cod_bank_merged_with")
    private String bankMergedWith;

    @Column(name = "flg_default_value")
    private String defaultValue;
}
