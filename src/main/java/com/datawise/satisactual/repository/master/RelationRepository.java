package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.RelationEmbeddedKey;
import com.datawise.satisactual.entities.master.RelationEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationRepository extends IBaseRepository<RelationEntity, RelationEmbeddedKey> {}
