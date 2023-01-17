package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.EthnicCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.EthnicCodeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EthnicCodeRepository extends IBaseRepository<EthnicCodeEntity, EthnicCodeEmbeddedKey> {}
