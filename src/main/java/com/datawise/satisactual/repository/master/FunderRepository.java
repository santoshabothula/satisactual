package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.FunderEmbeddedKey;
import com.datawise.satisactual.entities.master.FunderEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FunderRepository extends IBaseRepository<FunderEntity, FunderEmbeddedKey> {}
