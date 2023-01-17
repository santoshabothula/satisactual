package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.BankCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.BankCodeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankCodeRepository extends IBaseRepository<BankCodeEntity, BankCodeEmbeddedKey> {}
