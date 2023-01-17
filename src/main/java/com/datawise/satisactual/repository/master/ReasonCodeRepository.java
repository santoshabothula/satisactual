package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.ReasonCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.ReasonCodeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReasonCodeRepository extends IBaseRepository<ReasonCodeEntity, ReasonCodeEmbeddedKey> {}
