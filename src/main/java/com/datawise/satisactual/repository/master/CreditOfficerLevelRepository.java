package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.CreditOfficerLevelEmbeddedKey;
import com.datawise.satisactual.entities.master.CreditOfficerLevelEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditOfficerLevelRepository extends IBaseRepository<CreditOfficerLevelEntity, CreditOfficerLevelEmbeddedKey> {}
