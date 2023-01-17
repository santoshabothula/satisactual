package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.IncomeSrcEmbeddedKey;
import com.datawise.satisactual.entities.master.IncomeSrcEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeSrcRepository extends IBaseRepository<IncomeSrcEntity, IncomeSrcEmbeddedKey> {}
