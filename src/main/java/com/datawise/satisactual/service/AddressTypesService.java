package com.datawise.satisactual.service;

import com.datawise.satisactual.dto.AddressTypesMasterDTO;
import com.datawise.satisactual.dto.MakerCheckerDTO;
import com.datawise.satisactual.entities.AddressTypesEmbeddedKey;
import com.datawise.satisactual.entities.AddressTypesMaster;
import com.datawise.satisactual.entities.MakerChecker;
import com.datawise.satisactual.repository.AddressTypesRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class AddressTypesService {

    private final CommonMasterService<AddressTypesMasterDTO, AddressTypesMaster, AddressTypesEmbeddedKey> service;
    private final AddressTypesRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public AddressTypesService(AddressTypesRepository repository, CommonMasterService<AddressTypesMasterDTO, AddressTypesMaster, AddressTypesEmbeddedKey> service) {
        this.repository = repository;
        this.service = service;
        this.mapper = new ModelMapper();
    }

    public List<AddressTypesMasterDTO> getAll() {
        return service.getAll(repository, mapper, AddressTypesMasterDTO.class);
    }

    public AddressTypesMasterDTO get(String type, String status) {
        return service.get(repository, mapper, AddressTypesMasterDTO.class, prepareId(type, status));
    }

    public void save(AddressTypesMasterDTO dto) {
        save(dto, true);
    }

    public void update(AddressTypesMasterDTO dto) {
        save(dto, false);
    }

    @Transactional
    public void saveAll(List<AddressTypesMasterDTO> list) {
        list.forEach(this::save);
    }

    @Transactional
    public void updateAll(List<AddressTypesMasterDTO> list) { list.forEach(this::update); }

    public void delete(AddressTypesMasterDTO dto) {
        delete(dto.getCodAddressType(), dto.getCodRecordStatus());
    }

    public void delete(String type, String status) {
        service.delete(repository, prepareId(type, status));
    }

    private AddressTypesEmbeddedKey prepareId(String type, String status) {
        return AddressTypesEmbeddedKey.builder().codAddressType(type).codRecordStatus(status).build();
    }

    private void save(AddressTypesMasterDTO dto, boolean isSave) {
        AddressTypesEmbeddedKey id = prepareId(dto.getCodAddressType(), dto.getCodRecordStatus());
        AddressTypesMaster entity = isSave ?
                service.beforeSave(repository, mapper, AddressTypesMaster.class, id, dto) :
                service.beforeUpdate(repository, mapper, AddressTypesMaster.class, id, dto);
        entity.setId(id);
        service.save(repository, entity);
    }

    @PostConstruct
    void postConstruct() {
        TypeMap<AddressTypesMaster, AddressTypesMasterDTO> dtoMap = mapper.createTypeMap(AddressTypesMaster.class, AddressTypesMasterDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodAddressType(), AddressTypesMasterDTO::setCodAddressType);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), AddressTypesMasterDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerChecker::getLastMakerId, AddressTypesMasterDTO::setLastMakerId);
        dtoMap.addMapping(MakerChecker::getLastMakerDateTime, AddressTypesMasterDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerChecker::getLastCheckerId, AddressTypesMasterDTO::setLastCheckerId);
        dtoMap.addMapping(MakerChecker::getLastCheckerDateTime, AddressTypesMasterDTO::setLastCheckerDateTime);

        TypeMap<AddressTypesMasterDTO, AddressTypesMaster> entityMap = mapper.createTypeMap(AddressTypesMasterDTO.class, AddressTypesMaster.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, AddressTypesMaster::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, AddressTypesMaster::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, AddressTypesMaster::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, AddressTypesMaster::setLastCheckerDateTime);
    }

}
