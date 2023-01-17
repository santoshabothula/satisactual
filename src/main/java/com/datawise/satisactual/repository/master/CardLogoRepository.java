package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.CardLogoEmbeddedKey;
import com.datawise.satisactual.entities.master.CardLogoEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardLogoRepository extends IBaseRepository<CardLogoEntity, CardLogoEmbeddedKey> {}
