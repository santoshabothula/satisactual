package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.DepartmentSupportTeamEmbeddedKey;
import com.datawise.satisactual.entities.master.DepartmentSupportTeamEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentSupportTeamRepository extends IBaseRepository<DepartmentSupportTeamEntity, DepartmentSupportTeamEmbeddedKey> {}
