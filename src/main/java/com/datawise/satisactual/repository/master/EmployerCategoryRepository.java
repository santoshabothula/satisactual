package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.EmployerCategoryEmbeddedKey;
import com.datawise.satisactual.entities.master.EmployerCategoryEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerCategoryRepository extends IBaseRepository<EmployerCategoryEntity, EmployerCategoryEmbeddedKey> {}
