package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.AuditChecklistEmbeddedKey;
import com.datawise.satisactual.entities.master.AuditChecklistEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditChecklistRepository extends IBaseRepository<AuditChecklistEntity, AuditChecklistEmbeddedKey> {}
