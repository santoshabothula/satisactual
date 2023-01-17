package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.StmtCycleEmbeddedKey;
import com.datawise.satisactual.entities.master.StmtCycleEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StmtCycleRepository extends IBaseRepository<StmtCycleEntity, StmtCycleEmbeddedKey> {}
