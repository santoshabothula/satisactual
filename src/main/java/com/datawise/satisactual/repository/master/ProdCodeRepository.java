package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.ProdCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.ProdCodeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdCodeRepository extends IBaseRepository<ProdCodeEntity, ProdCodeEmbeddedKey> {}
