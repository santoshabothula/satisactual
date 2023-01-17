package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.CountryCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.CountryCodeEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryCodeRepository extends IBaseRepository<CountryCodeEntity, CountryCodeEmbeddedKey> {}
