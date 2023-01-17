package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.CustAttributeEmbeddedKey;
import com.datawise.satisactual.entities.master.CustAttributeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustAttributeRepository extends IBaseRepository<CustAttributeEntity, CustAttributeEmbeddedKey> {}
