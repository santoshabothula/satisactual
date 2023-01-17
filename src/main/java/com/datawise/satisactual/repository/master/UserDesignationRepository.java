package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.UserDesignationEmbeddedKey;
import com.datawise.satisactual.entities.master.UserDesignationEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDesignationRepository extends IBaseRepository<UserDesignationEntity, UserDesignationEmbeddedKey> {}
