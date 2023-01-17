package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.AssignmentTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.AssignmentTypeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentTypeRepository extends IBaseRepository<AssignmentTypeEntity, AssignmentTypeEmbeddedKey> {}
