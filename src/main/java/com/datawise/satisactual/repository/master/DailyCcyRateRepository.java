package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.DailyCcyRateEmbeddedKey;
import com.datawise.satisactual.entities.master.DailyCcyRateEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyCcyRateRepository extends IBaseRepository<DailyCcyRateEntity, DailyCcyRateEmbeddedKey> {}
