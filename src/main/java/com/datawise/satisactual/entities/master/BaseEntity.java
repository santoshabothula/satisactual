package com.datawise.satisactual.entities.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseEntity extends MakerCheckerEntity {
    @Column(name = "cod_rec_status")
    private String codRecordStatus;
}
