package com.datawise.satisactual.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mst_addr_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressTypesMaster extends MakerChecker {

    @EmbeddedId
    private AddressTypesEmbeddedKey id;

    @Column(name = "txt_addr_type_desc")
    private String addressTypeDesc;

    @Column(name = "flg_default_value")
    private String defaultValue;

    @Column(name = "flg_accept_as_primary")
    private String acceptAsPrimary;

    @Column(name = "flg_accept_for_individual")
    private String acceptForIndividual;

    @Column(name = "flg_accept_for_organization")
    private String acceptForOrganization;
}
