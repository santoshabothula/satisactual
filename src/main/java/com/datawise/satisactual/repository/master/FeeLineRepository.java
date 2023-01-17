package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.FeeLineEmbeddedKey;
import com.datawise.satisactual.entities.master.FeeLineEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeLineRepository extends IBaseRepository<FeeLineEntity, FeeLineEmbeddedKey> {}
