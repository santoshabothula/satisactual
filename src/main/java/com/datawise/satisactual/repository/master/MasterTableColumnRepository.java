package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.MasterTableColumnEmbeddedKey;
import com.datawise.satisactual.entities.master.MasterTableColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterTableColumnRepository extends JpaRepository<MasterTableColumnEntity, MasterTableColumnEmbeddedKey> {}
