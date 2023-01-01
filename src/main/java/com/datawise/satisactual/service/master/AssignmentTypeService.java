package com.datawise.satisactual.service.master;

import com.datawise.satisactual.model.master.dto.AssignmentTypeDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.entities.AssignmentTypeEmbeddedKey;
import com.datawise.satisactual.entities.AssignmentTypeMaster;
import com.datawise.satisactual.entities.MakerChecker;
import com.datawise.satisactual.repository.master.AssignmentTypeRepository;
import com.datawise.satisactual.service.CommonMasterService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class AssignmentTypeService {

    private final CommonMasterService<AssignmentTypeDTO, AssignmentTypeMaster, AssignmentTypeEmbeddedKey> service;
    private final AssignmentTypeRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public AssignmentTypeService(CommonMasterService<AssignmentTypeDTO, AssignmentTypeMaster, AssignmentTypeEmbeddedKey> service, AssignmentTypeRepository repository) {
        this.service = service;
        this.repository = repository;
        mapper = new ModelMapper();
    }

    public List<AssignmentTypeDTO> getAll() {
        return service.getAll(repository, mapper, AssignmentTypeDTO.class);
    }

    public AssignmentTypeDTO get(String type, String status) {
        return service.get(repository, mapper, AssignmentTypeDTO.class, prepareId(type, status));
    }

    public void save(AssignmentTypeDTO dto) {
        save(dto, true);
    }

    public void update(AssignmentTypeDTO dto) {
        save(dto, false);
    }

    @Transactional
    public void saveAll(List<AssignmentTypeDTO> list) {
        list.forEach(this::save);
    }

    @Transactional
    public void updateAll(List<AssignmentTypeDTO> list) { list.forEach(this::update); }

    public void delete(AssignmentTypeDTO dto) {
        delete(dto.getCodAssignmentType(), dto.getCodRecordStatus());
    }

    public void delete(String type, String status) {
        service.delete(repository, prepareId(type, status));
    }

    private AssignmentTypeEmbeddedKey prepareId(String type, String status) {
        return AssignmentTypeEmbeddedKey.builder().codAssignmentType(type).codRecordStatus(status).build();
    }

    private void save(AssignmentTypeDTO dto, boolean isSave) {
        AssignmentTypeEmbeddedKey id = prepareId(dto.getCodAssignmentType(), dto.getCodRecordStatus());
        AssignmentTypeMaster entity = isSave ?
                service.beforeSave(repository, mapper, AssignmentTypeMaster.class, id, dto) :
                service.beforeUpdate(repository, mapper, AssignmentTypeMaster.class, id, dto);
        entity.setId(id);
        service.save(repository, entity);
    }

    @PostConstruct
    void postConstruct() {
        TypeMap<AssignmentTypeMaster, AssignmentTypeDTO> dtoMap = mapper.createTypeMap(AssignmentTypeMaster.class, AssignmentTypeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodAssignmentType(), AssignmentTypeDTO::setCodAssignmentType);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), AssignmentTypeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerChecker::getLastMakerId, AssignmentTypeDTO::setLastMakerId);
        dtoMap.addMapping(MakerChecker::getLastMakerDateTime, AssignmentTypeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerChecker::getLastCheckerId, AssignmentTypeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerChecker::getLastCheckerDateTime, AssignmentTypeDTO::setLastCheckerDateTime);

        TypeMap<AssignmentTypeDTO, AssignmentTypeMaster> entityMap = mapper.createTypeMap(AssignmentTypeDTO.class, AssignmentTypeMaster.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, AssignmentTypeMaster::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, AssignmentTypeMaster::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, AssignmentTypeMaster::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, AssignmentTypeMaster::setLastCheckerDateTime);
    }
}
