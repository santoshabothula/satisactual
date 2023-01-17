package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.DepartmentEmbeddedKey;
import com.datawise.satisactual.entities.master.DepartmentEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends IBaseRepository<DepartmentEntity, DepartmentEmbeddedKey> {}
