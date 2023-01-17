package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.BlockCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.BlockCodeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockCodeRepository extends IBaseRepository<BlockCodeEntity, BlockCodeEmbeddedKey> {}
