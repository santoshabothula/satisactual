package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.AssignmentTypeEmbeddedKey;
import com.datawise.satisactual.entities.AssignmentTypeMaster;
import com.datawise.satisactual.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentTypeRepository extends BaseRepository<AssignmentTypeMaster, AssignmentTypeEmbeddedKey> {}
