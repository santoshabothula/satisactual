package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.MajorCityEmbeddedKey;
import com.datawise.satisactual.entities.master.MajorCityEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorCityRepository extends IBaseRepository<MajorCityEntity, MajorCityEmbeddedKey> {}
