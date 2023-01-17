package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.VehicleModelEmbeddedKey;
import com.datawise.satisactual.entities.master.VehicleModelEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleModelRepository extends IBaseRepository<VehicleModelEntity, VehicleModelEmbeddedKey> {}
