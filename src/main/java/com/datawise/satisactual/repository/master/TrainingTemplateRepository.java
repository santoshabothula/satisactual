package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.TrainingTemplateEmbeddedKey;
import com.datawise.satisactual.entities.master.TrainingTemplateEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingTemplateRepository extends IBaseRepository<TrainingTemplateEntity, TrainingTemplateEmbeddedKey> {}
