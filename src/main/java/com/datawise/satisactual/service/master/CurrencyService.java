package com.datawise.satisactual.service.master;

import com.datawise.satisactual.model.master.dto.CurrencyDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.entities.Currency;
import com.datawise.satisactual.entities.CurrencyEmbeddedKey;
import com.datawise.satisactual.entities.MakerChecker;
import com.datawise.satisactual.exception.SatisActualProcessException;
import com.datawise.satisactual.repository.master.CurrencyRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    private final CurrencyRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public CurrencyService(CurrencyRepository repository) {
        this.repository = repository;
        this.mapper = new ModelMapper();
    }

    @Transactional(readOnly = true)
    public List<CurrencyDTO> getAll() {
        return repository.findAll().stream().map(currency -> mapper.map(currency, CurrencyDTO.class)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CurrencyDTO get(String currency, String status) {
        return mapper.map(getEntity(currency, status), CurrencyDTO.class);
    }

    private Currency getEntity(String currency, String status) {
        Currency entity = getOptionalEntity(currency, status);
        if (Objects.isNull(entity)) throw new SatisActualProcessException("Record not found!");
        return entity;
    }

    private Currency getOptionalEntity(String currency, String status) {
        return repository.findById(CurrencyEmbeddedKey.builder().codCurrency(currency).codRecordStatus(status).build()).orElse(null);
    }

    @Transactional
    public void save(CurrencyDTO currencyDTO) {
        if (Objects.nonNull(getOptionalEntity(currencyDTO.getCodCurrency(), currencyDTO.getCodRecordStatus()))) {
            throw new SatisActualProcessException("Currency already exists!");
        }

        Currency currency = mapper.map(currencyDTO, Currency.class);
        currency.setId(CurrencyEmbeddedKey.builder().codCurrency(currencyDTO.getCodCurrency()).codRecordStatus(currencyDTO.getCodRecordStatus()).build());
        repository.save(currency);
    }

    @Transactional
    public void saveAll(List<CurrencyDTO> list) {
        list.forEach(this::save);
    }

    @Transactional
    public void update(CurrencyDTO currencyDTO) {
        if (Objects.isNull(get(currencyDTO.getCodCurrency(), currencyDTO.getCodRecordStatus()))) {
            throw new SatisActualProcessException("Currency does not exists!");
        }

        Currency currency = mapper.map(currencyDTO, Currency.class);
        currency.setId(CurrencyEmbeddedKey.builder().codCurrency(currencyDTO.getCodCurrency()).codRecordStatus(currencyDTO.getCodRecordStatus()).build());
        repository.save(currency);
    }

    @Transactional
    public void updateAll(List<CurrencyDTO> list) {
        list.forEach(this::update);
    }

    @Transactional
    public void delete(CurrencyDTO currencyDTO) {
        repository.delete(getEntity(currencyDTO.getCodCurrency(), currencyDTO.getCodRecordStatus()));
    }

    @Transactional
    public void delete(String currency, String status) {
        repository.delete(getEntity(currency, status));
    }

    @PostConstruct
    public void postConstruct() {
        TypeMap<Currency, CurrencyDTO> dtoMap = mapper.createTypeMap(Currency.class, CurrencyDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodCurrency(), CurrencyDTO::setCodCurrency);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), CurrencyDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerChecker::getLastMakerId, CurrencyDTO::setLastMakerId);
        dtoMap.addMapping(MakerChecker::getLastMakerDateTime, CurrencyDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerChecker::getLastCheckerId, CurrencyDTO::setLastCheckerId);
        dtoMap.addMapping(MakerChecker::getLastCheckerDateTime, CurrencyDTO::setLastCheckerDateTime);

        TypeMap<CurrencyDTO, Currency> entityMap = mapper.createTypeMap(CurrencyDTO.class, Currency.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, Currency::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, Currency::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, Currency::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, Currency::setLastCheckerDateTime);
    }
}
