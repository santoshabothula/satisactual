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
@Table(name = "mst_state_codes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StateCodeEntity extends MakerCheckerEntity {

    @EmbeddedId
    private StateCodeEmbeddedKey id;

    @Column(name = "txt_state_short_code")
    private String stateShortCode;

    @Column(name = "txt_state_name")
    private String stateName;

    @Column(name = "flg_default_value")
    private String defaultValue;
}