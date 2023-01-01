package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.AddressTypesEmbeddedKey;
import com.datawise.satisactual.entities.AddressTypesMaster;
import com.datawise.satisactual.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressTypesRepository extends BaseRepository<AddressTypesMaster, AddressTypesEmbeddedKey> {}
