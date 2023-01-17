package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.CustomModelAttribEmbeddedKey;
import com.datawise.satisactual.entities.master.CustomModelAttribEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomModelAttribRepository extends IBaseRepository<CustomModelAttribEntity, CustomModelAttribEmbeddedKey> {}
