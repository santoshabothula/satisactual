package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.PinCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.PinCodeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PinCodeRepository extends IBaseRepository<PinCodeEntity, PinCodeEmbeddedKey> {}
