package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.OrgTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.OrgTypeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgTypeRepository extends IBaseRepository<OrgTypeEntity, OrgTypeEmbeddedKey> {}
