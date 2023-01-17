package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.PrioritySectorCategoryEmbeddedKey;
import com.datawise.satisactual.entities.master.PrioritySectorCategoryEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrioritySectorCategoryRepository extends IBaseRepository<PrioritySectorCategoryEntity, PrioritySectorCategoryEmbeddedKey> {}
