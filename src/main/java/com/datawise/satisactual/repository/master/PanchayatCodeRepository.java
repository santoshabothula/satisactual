package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.PanchayatCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.PanchayatCodeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanchayatCodeRepository extends IBaseRepository<PanchayatCodeEntity, PanchayatCodeEmbeddedKey> {}
