package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.BankBranchEmbeddedKey;
import com.datawise.satisactual.entities.master.BankBranchEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankBranchRepository extends IBaseRepository<BankBranchEntity, BankBranchEmbeddedKey> {}
