package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.CovenantCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.CovenantCodeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CovenantCodeRepository extends IBaseRepository<CovenantCodeEntity, CovenantCodeEmbeddedKey> {}
