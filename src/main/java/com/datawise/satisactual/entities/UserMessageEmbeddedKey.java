package com.datawise.satisactual.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMessageEmbeddedKey implements Serializable {

    private static final long serialVersionUID = 1992707390007470275L;

    @Column(name = "num_message")
    private Integer numMessage;

    @Column(name = "cod_language")
    private String codLanguage;
}
