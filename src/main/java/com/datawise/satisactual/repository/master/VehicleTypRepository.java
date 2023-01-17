package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.VehicleTypEmbeddedKey;
import com.datawise.satisactual.entities.master.VehicleTypEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleTypRepository extends IBaseRepository<VehicleTypEntity, VehicleTypEmbeddedKey> {}
