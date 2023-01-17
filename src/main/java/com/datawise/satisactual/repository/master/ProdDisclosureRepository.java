package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.ProdDisclosureEmbeddedKey;
import com.datawise.satisactual.entities.master.ProdDisclosureEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdDisclosureRepository extends IBaseRepository<ProdDisclosureEntity, ProdDisclosureEmbeddedKey> {}
