package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.MinorStatusCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.MinorStatusCodeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MinorStatusCodeRepository extends IBaseRepository<MinorStatusCodeEntity, MinorStatusCodeEmbeddedKey> {}
