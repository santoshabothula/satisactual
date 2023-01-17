package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.TrainingTemplateModuleEmbeddedKey;
import com.datawise.satisactual.entities.master.TrainingTemplateModuleEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingTemplateModuleRepository extends IBaseRepository<TrainingTemplateModuleEntity, TrainingTemplateModuleEmbeddedKey> {}
