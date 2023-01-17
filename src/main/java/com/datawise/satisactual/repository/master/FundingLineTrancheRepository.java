package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.FundingLineTrancheEmbeddedKey;
import com.datawise.satisactual.entities.master.FundingLineTrancheEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundingLineTrancheRepository extends IBaseRepository<FundingLineTrancheEntity, FundingLineTrancheEmbeddedKey> {}
