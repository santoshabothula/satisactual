package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.ThirdPartyTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.ThirdPartyTypeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThirdPartyTypeRepository extends IBaseRepository<ThirdPartyTypeEntity, ThirdPartyTypeEmbeddedKey> {}
