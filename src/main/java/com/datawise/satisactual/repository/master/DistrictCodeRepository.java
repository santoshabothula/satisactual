package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.DistrictCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.DistrictCodeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictCodeRepository extends IBaseRepository<DistrictCodeEntity, DistrictCodeEmbeddedKey> {}
