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
@Table(name = "mst_funding_lines")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FundingLineEntity extends MakerCheckerEntity {

    @EmbeddedId
    private FundingLineEmbeddedKey id;

    @Column(name = "txt_funding_line_desc")
    private String fundingLineDesc;

    @Column(name = "cod_currency")
    private String currency;

    @Column(name = "amt_funding_line")
    private Double fundingLine;

    @Column(name = "amt_funding_line_fcy")
    private Double fundingLineFcy;

    @Column(name = "dat_sanction")
    private LocalDate sanction;

    @Column(name = "txt_instructions")
    private String instructions;

    @Column(name = "flg_reserved")
    private String reserved;

    @Column(name = "flg_default_value")
    private String defaultValue;

}