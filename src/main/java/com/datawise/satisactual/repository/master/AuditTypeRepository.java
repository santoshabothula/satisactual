package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.AuditTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.AuditTypeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditTypeRepository extends IBaseRepository<AuditTypeEntity, AuditTypeEmbeddedKey> {}
