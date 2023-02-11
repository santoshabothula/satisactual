package com.datawise.satisactual.repository.custom.report;

import com.datawise.satisactual.entities.custom.report.ReportOptionEmbeddedKey;
import com.datawise.satisactual.entities.custom.report.ReportOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportOptionRepository extends JpaRepository<ReportOptionEntity, ReportOptionEmbeddedKey> {}