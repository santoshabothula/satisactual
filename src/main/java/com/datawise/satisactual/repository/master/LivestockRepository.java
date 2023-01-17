package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.LivestockEmbeddedKey;
import com.datawise.satisactual.entities.master.LivestockEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivestockRepository extends IBaseRepository<LivestockEntity, LivestockEmbeddedKey> {}
