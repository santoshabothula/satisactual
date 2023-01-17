package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.FinRatioEmbeddedKey;
import com.datawise.satisactual.entities.master.FinRatioEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinRatioRepository extends IBaseRepository<FinRatioEntity, FinRatioEmbeddedKey> {}
