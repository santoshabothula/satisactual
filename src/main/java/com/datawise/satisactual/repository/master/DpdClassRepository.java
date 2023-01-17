package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.DpdClassEmbeddedKey;
import com.datawise.satisactual.entities.master.DpdClassEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DpdClassRepository extends IBaseRepository<DpdClassEntity, DpdClassEmbeddedKey> {}
