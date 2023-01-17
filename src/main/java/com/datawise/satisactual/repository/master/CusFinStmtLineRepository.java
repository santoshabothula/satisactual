package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.CusFinStmtLineEmbeddedKey;
import com.datawise.satisactual.entities.master.CusFinStmtLineEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CusFinStmtLineRepository extends IBaseRepository<CusFinStmtLineEntity, CusFinStmtLineEmbeddedKey> {}
