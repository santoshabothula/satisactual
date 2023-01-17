package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.CovenantTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.CovenantTypeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CovenantTypeRepository extends IBaseRepository<CovenantTypeEntity, CovenantTypeEmbeddedKey> {}
