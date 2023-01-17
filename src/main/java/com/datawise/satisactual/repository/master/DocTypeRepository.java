package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.DocTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.DocTypeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocTypeRepository extends IBaseRepository<DocTypeEntity, DocTypeEmbeddedKey> {}
