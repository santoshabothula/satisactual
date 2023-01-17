package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.ContactOutcomeEmbeddedKey;
import com.datawise.satisactual.entities.master.ContactOutcomeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactOutcomeRepository extends IBaseRepository<ContactOutcomeEntity, ContactOutcomeEmbeddedKey> {}
