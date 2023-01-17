package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.ReligionEmbeddedKey;
import com.datawise.satisactual.entities.master.ReligionEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReligionRepository extends IBaseRepository<ReligionEntity, ReligionEmbeddedKey> {}
