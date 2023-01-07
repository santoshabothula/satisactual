package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.AddressTypesEmbeddedKey;
import com.datawise.satisactual.entities.master.AddressTypeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressTypesRepository extends IBaseRepository<AddressTypeEntity, AddressTypesEmbeddedKey> {}
