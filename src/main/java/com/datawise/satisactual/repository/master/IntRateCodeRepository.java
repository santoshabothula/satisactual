package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.IntRateCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.IntRateCodeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntRateCodeRepository extends IBaseRepository<IntRateCodeEntity, IntRateCodeEmbeddedKey> {}
