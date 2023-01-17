package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.CustAttributeGroupEmbeddedKey;
import com.datawise.satisactual.entities.master.CustAttributeGroupEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustAttributeGroupRepository extends IBaseRepository<CustAttributeGroupEntity, CustAttributeGroupEmbeddedKey> {}
