package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.ProdDocEmbeddedKey;
import com.datawise.satisactual.entities.master.ProdDocEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdDocRepository extends IBaseRepository<ProdDocEntity, ProdDocEmbeddedKey> {}
