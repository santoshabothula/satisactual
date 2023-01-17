package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.DisclosureEmbeddedKey;
import com.datawise.satisactual.entities.master.DisclosureEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisclosureRepository extends IBaseRepository<DisclosureEntity, DisclosureEmbeddedKey> {}
