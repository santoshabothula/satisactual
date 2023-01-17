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
@Table(name = "mst_ranks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RankEntity extends MakerCheckerEntity {

    @EmbeddedId
    private RankEmbeddedKey id;

    @Column(name = "txt_rank_desc")
    private String rankDesc;

    @Column(name = "num_seniority")
    private Integer seniority;

    @Column(name = "flg_officer")
    private String officer;
}