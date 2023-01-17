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
@Table(name = "mst_txn_codes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TxnCodeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private TxnCodeEmbeddedKey id;

    @Column(name = "txt_txn_short_desc")
    private String txnShortDesc;

    @Column(name = "txt_txn_long_desc")
    private String txnLongDesc;

    @Column(name = "flg_auth_mandatory")
    private String authMandatory;

    @Column(name = "flg_dual_key_mandatory")
    private String dualKeyMandatory;

    @Column(name = "enu_txn_mode")
    private String txnMode;
}