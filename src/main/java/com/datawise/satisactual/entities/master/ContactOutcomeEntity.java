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
@Table(name = "mst_contact_outcome")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactOutcomeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private ContactOutcomeEmbeddedKey id;

    @Column(name = "txt_contact_outcome_desc")
    private String contactOutcomeDesc;

    @Column(name = "cod_parent_outcome")
    private String parentOutcome;

    @Column(name = "enu_base_channel_type")
    private String baseChannelType;

    @Column(name = "flg_valid_outcome")
    private String validOutcome;

    @Column(name = "flg_outbound")
    private String outbound;

}