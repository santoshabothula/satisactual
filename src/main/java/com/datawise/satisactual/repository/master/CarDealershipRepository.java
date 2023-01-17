package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.CarDealershipEmbeddedKey;
import com.datawise.satisactual.entities.master.CarDealershipEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDealershipRepository extends IBaseRepository<CarDealershipEntity, CarDealershipEmbeddedKey> {}
