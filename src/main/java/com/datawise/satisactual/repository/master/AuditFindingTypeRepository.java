package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.AuditFindingsTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.AuditFindingsTypeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditFindingTypeRepository extends IBaseRepository<AuditFindingsTypeEntity, AuditFindingsTypeEmbeddedKey> {}
