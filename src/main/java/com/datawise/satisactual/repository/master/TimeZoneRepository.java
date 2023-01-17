package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.TimeZoneEmbeddedKey;
import com.datawise.satisactual.entities.master.TimeZoneEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeZoneRepository extends IBaseRepository<TimeZoneEntity, TimeZoneEmbeddedKey> {}
