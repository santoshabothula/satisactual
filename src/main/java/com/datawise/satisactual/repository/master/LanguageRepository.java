package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.LanguageEmbeddedKey;
import com.datawise.satisactual.entities.master.LanguageEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends IBaseRepository<LanguageEntity, LanguageEmbeddedKey> {}
