package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.CenterTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.CenterTypeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CenterTypeRepository extends IBaseRepository<CenterTypeEntity, CenterTypeEmbeddedKey> {}
