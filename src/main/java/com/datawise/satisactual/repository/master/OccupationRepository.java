package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.OccupationEmbeddedKey;
import com.datawise.satisactual.entities.master.OccupationEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OccupationRepository extends IBaseRepository<OccupationEntity, OccupationEmbeddedKey> {}
