package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.StateCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.StateCodeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateCodeRepository extends IBaseRepository<StateCodeEntity, StateCodeEmbeddedKey> {}
