package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.BankTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.BankTypeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankTypeRepository extends IBaseRepository<BankTypeEntity, BankTypeEmbeddedKey> {}
