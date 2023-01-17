package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.CurrencyEmbeddedKey;
import com.datawise.satisactual.entities.master.CurrencyEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends IBaseRepository<CurrencyEntity, CurrencyEmbeddedKey> {}
