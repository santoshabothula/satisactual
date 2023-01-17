package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.QuestionTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.QuestionTypeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionTypeRepository extends IBaseRepository<QuestionTypeEntity, QuestionTypeEmbeddedKey> {}
