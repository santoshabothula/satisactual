package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.PrefixEntity;
import com.datawise.satisactual.entities.master.PrefixEmbeddedKey;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrefixRepository extends IBaseRepository<PrefixEntity, PrefixEmbeddedKey> {}
