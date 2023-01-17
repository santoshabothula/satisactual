package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.CityClassEmbeddedKey;
import com.datawise.satisactual.entities.master.CityClassEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityClassRepository extends IBaseRepository<CityClassEntity, CityClassEmbeddedKey> {}
