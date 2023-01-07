package com.datawise.satisactual.entities.master;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class MakerCheckerEntity {
    @Column(name = "txt_last_maker_id")
    private String lastMakerId;

    @Column(name = "dat_last_maker")
    private LocalDateTime lastMakerDateTime;

    @Column(name = "txt_last_checker_id")
    private String lastCheckerId;

    @Column(name = "dat_last_checker")
    private LocalDateTime lastCheckerDateTime;
}
