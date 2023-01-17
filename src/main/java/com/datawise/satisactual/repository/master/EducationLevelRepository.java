package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.EducationLevelEmbeddedKey;
import com.datawise.satisactual.entities.master.EducationLevelEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationLevelRepository extends IBaseRepository<EducationLevelEntity, EducationLevelEmbeddedKey> {}
