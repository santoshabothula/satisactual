package com.datawise.satisactual.repository;

import com.datawise.satisactual.entities.MasterTableColumnEmbeddedKey;
import com.datawise.satisactual.entities.MasterTableColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterTableColumnRepository extends JpaRepository<MasterTableColumnEntity, MasterTableColumnEmbeddedKey> {
    List<MasterTableColumnEntity> findByIdTableNameAndIdCodRecStatus(String tableName, String codRecStatus);
}
