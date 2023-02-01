package com.datawise.satisactual.repository;

import com.datawise.satisactual.entities.master.MasterTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterTableRepository extends JpaRepository<MasterTableEntity, String> {
    MasterTableEntity findByTableNameAndCodRecordStatus(String tableName, String status);
    List<MasterTableEntity> findByCodRecordStatus(String status);
}
