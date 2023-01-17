package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.FundingLineEmbeddedKey;
import com.datawise.satisactual.entities.master.FundingLineEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundingLineRepository extends IBaseRepository<FundingLineEntity, FundingLineEmbeddedKey> {}
