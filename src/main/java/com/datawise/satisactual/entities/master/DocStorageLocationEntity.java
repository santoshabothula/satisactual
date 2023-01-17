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
@Table(name = "mst_doc_storage_location")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocStorageLocationEntity extends MakerCheckerEntity {

    @EmbeddedId
    private DocStorageLocationEmbeddedKey id;

    @Column(name = "txt_storage_location_desc")
    private String storageLocationDesc;

    @Column(name = "txt_security_class")
    private String securityClass;

    @Column(name = "flg_default_value")
    private String defaultValue;

}