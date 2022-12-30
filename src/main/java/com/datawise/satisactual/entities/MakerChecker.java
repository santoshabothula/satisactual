package com.datawise.satisactual.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class MakerChecker {

    @Column(name = "txt_last_maker_id")
    private String lastMakerId;

    @CreationTimestamp
    @Column(name = "dat_last_maker")
    private Date lastMakerDateTime;

    @Column(name = "txt_last_checker_id")
    private String lastCheckerId;

    @UpdateTimestamp
    @Column(name = "dat_last_checker")
    private Date lastCheckerDateTime;
}
