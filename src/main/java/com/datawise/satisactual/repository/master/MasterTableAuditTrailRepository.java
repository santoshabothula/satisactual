package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.MasterTableAuditTrailEmbeddedKey;
import com.datawise.satisactual.entities.master.MasterTableAuditTrailMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterTableAuditTrailRepository extends JpaRepository<MasterTableAuditTrailMaster, MasterTableAuditTrailEmbeddedKey> {}
