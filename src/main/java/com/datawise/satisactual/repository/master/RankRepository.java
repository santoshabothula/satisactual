package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.RankEmbeddedKey;
import com.datawise.satisactual.entities.master.RankEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankRepository extends IBaseRepository<RankEntity, RankEmbeddedKey> {}
