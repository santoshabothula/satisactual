package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.IntRateEmbeddedKey;
import com.datawise.satisactual.entities.master.IntRateEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntRateRepository extends IBaseRepository<IntRateEntity, IntRateEmbeddedKey> {}
