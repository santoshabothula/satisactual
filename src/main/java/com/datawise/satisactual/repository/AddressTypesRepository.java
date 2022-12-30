package com.datawise.satisactual.repository;

import com.datawise.satisactual.entities.AddressTypesEmbeddedKey;
import com.datawise.satisactual.entities.AddressTypesMaster;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressTypesRepository extends BaseRepository<AddressTypesMaster, AddressTypesEmbeddedKey> {}
