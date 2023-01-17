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
@Table(name = "mst_funding_line_tranches")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FundingLineTrancheEntity extends MakerCheckerEntity {

    @EmbeddedId
    private FundingLineTrancheEmbeddedKey id;

    @Column(name = "txt_tranche_desc")
    private String trancheDesc;

    @Column(name = "cod_assignment_typ")
    private String assignmentTyp;

    @Column(name = "cod_currency")
    private String currency;

    @Column(name = "amt_tranche")
    private Double tranche;

    @Column(name = "amt_funding_line_fcy")
    private Double fundingLineFcy;

    @Column(name = "dat_disbursed")
    private LocalDate disbursed;

    @Column(name = "dat_first_payment")
    private LocalDate firstPayment;

    @Column(name = "dat_last_payment")
    private LocalDate lastPayment;

    @Column(name = "rat_interest")
    private Double ratInterest;

    @Column(name = "flg_default_value")
    private String defaultValue;

    @Column(name = "cod_ifsc_bank_branch")
    private String ifscBankBranch;

    @Column(name = "num_nostro_account")
    private String nostroAccount;
}