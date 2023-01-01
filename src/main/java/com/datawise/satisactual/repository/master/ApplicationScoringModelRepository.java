package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.ApplicationScoringModelEmbeddedKey;
import com.datawise.satisactual.entities.ApplicationScoringModelMaster;
import com.datawise.satisactual.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationScoringModelRepository extends BaseRepository<ApplicationScoringModelMaster, ApplicationScoringModelEmbeddedKey> {}
