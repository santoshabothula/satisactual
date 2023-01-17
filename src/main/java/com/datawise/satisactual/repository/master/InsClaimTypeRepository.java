package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.InsClaimTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.InsClaimTypeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsClaimTypeRepository extends IBaseRepository<InsClaimTypeEntity, InsClaimTypeEmbeddedKey> {}
