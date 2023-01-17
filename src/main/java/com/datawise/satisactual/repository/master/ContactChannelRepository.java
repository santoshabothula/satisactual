package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.ContactChannelEmbeddedKey;
import com.datawise.satisactual.entities.master.ContactChannelEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactChannelRepository extends IBaseRepository<ContactChannelEntity, ContactChannelEmbeddedKey> {}
