package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.VehicleMakeEmbeddedKey;
import com.datawise.satisactual.entities.master.VehicleMakeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleMakeRepository extends IBaseRepository<VehicleMakeEntity, VehicleMakeEmbeddedKey> {}
