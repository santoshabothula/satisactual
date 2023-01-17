package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.PromoCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.PromoCodeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoCodeRepository extends IBaseRepository<PromoCodeEntity, PromoCodeEmbeddedKey> {}
