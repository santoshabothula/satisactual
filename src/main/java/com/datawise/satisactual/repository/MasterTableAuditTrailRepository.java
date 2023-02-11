package com.datawise.satisactual.repository;

import com.datawise.satisactual.entities.MasterTableAuditTrailEmbeddedKey;
import com.datawise.satisactual.entities.MasterTableAuditTrailMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterTableAuditTrailRepository extends JpaRepository<MasterTableAuditTrailMaster, MasterTableAuditTrailEmbeddedKey> {}
