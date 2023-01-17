package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.DisclosureLangEmbeddedKey;
import com.datawise.satisactual.entities.master.DisclosureLangEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisclosureLangRepository extends IBaseRepository<DisclosureLangEntity, DisclosureLangEmbeddedKey> {}
