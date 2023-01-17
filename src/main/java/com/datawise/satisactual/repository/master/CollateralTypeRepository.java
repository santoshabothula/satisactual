package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.CollateralTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.CollateralTypeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollateralTypeRepository extends IBaseRepository<CollateralTypeEntity, CollateralTypeEmbeddedKey> {}
