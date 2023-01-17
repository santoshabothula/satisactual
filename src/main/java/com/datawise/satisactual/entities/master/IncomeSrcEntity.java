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
@Table(name = "mst_income_src")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncomeSrcEntity extends MakerCheckerEntity {

    @EmbeddedId
    private IncomeSrcEmbeddedKey id;

    @Column(name = "txt_income_src_desc")
    private String incomeSrcDesc;

    @Column(name = "flg_default_value")
    private String defaultValue;

    @Column(name = "num_max_debt_cover_verified")
    private Integer maxDebtCoverVerified;

    @Column(name = "num_max_debt_cover")
    private Integer maxDebtCover;

    @Column(name = "enu_addl_details_type")
    private String addlDetailsType;
}