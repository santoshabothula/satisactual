package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.ThirdPartyEmbeddedKey;
import com.datawise.satisactual.entities.master.ThirdPartyEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThirdPartyRepository extends IBaseRepository<ThirdPartyEntity, ThirdPartyEmbeddedKey> {}
