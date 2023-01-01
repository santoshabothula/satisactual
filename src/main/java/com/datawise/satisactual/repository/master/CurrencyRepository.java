package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.Currency;
import com.datawise.satisactual.entities.CurrencyEmbeddedKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, CurrencyEmbeddedKey> {}
