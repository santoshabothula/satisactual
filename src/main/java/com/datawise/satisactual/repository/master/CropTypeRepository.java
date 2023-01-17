package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.CropTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.CropTypeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CropTypeRepository extends IBaseRepository<CropTypeEntity, CropTypeEmbeddedKey> {}
