package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.FinRatiosIndBenchmarkEmbeddedKey;
import com.datawise.satisactual.entities.master.FinRatiosIndBenchmarkEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinRatiosIndBenchmarkRepository extends IBaseRepository<FinRatiosIndBenchmarkEntity, FinRatiosIndBenchmarkEmbeddedKey> {}
