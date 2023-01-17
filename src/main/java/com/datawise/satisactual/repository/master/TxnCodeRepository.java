package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.TxnCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.TxnCodeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TxnCodeRepository extends IBaseRepository<TxnCodeEntity, TxnCodeEmbeddedKey> {}
