package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.ApplicationScoringModelEmbeddedKey;
import com.datawise.satisactual.entities.master.ApplicationScoringModelEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationScoringModelRepository extends IBaseRepository<ApplicationScoringModelEntity, ApplicationScoringModelEmbeddedKey> {}
