package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.RegdEmployerEmbeddedKey;
import com.datawise.satisactual.entities.master.RegdEmployerEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegdEmployerRepository extends IBaseRepository<RegdEmployerEntity, RegdEmployerEmbeddedKey> {}
