package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.IndustryGrpEmbeddedKey;
import com.datawise.satisactual.entities.master.IndustryGrpEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryGrpRepository extends IBaseRepository<IndustryGrpEntity, IndustryGrpEmbeddedKey> {}
