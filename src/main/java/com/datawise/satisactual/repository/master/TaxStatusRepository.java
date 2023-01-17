package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.TaxStatusEmbeddedKey;
import com.datawise.satisactual.entities.master.TaxStatusEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxStatusRepository extends IBaseRepository<TaxStatusEntity, TaxStatusEmbeddedKey> {}
