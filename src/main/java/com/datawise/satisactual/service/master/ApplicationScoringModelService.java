package com.datawise.satisactual.service.master;

import com.datawise.satisactual.entities.ApplicationScoringModelEmbeddedKey;
import com.datawise.satisactual.entities.ApplicationScoringModelMaster;
import com.datawise.satisactual.entities.MakerChecker;
import com.datawise.satisactual.model.master.dto.ApplicationScoringModelDTO;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.ApplicationScoringModelRepository;
import com.datawise.satisactual.service.CommonMasterService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@Service
public class ApplicationScoringModelService {

    private final CommonMasterService<ApplicationScoringModelDTO, ApplicationScoringModelMaster, ApplicationScoringModelEmbeddedKey> service;
    private final ApplicationScoringModelRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public ApplicationScoringModelService(ApplicationScoringModelRepository repository, CommonMasterService<ApplicationScoringModelDTO, ApplicationScoringModelMaster, ApplicationScoringModelEmbeddedKey> service) {
        this.repository = repository;
        this.service = service;
        this.mapper = new ModelMapper();
    }

    public List<ApplicationScoringModelDTO> getAll() {
        return service.getAll(repository, mapper, ApplicationScoringModelDTO.class);
    }

    public ApplicationScoringModelDTO get(String type, String status, LocalDate date) {
        return service.get(repository, mapper, ApplicationScoringModelDTO.class, prepareId(type, status, date));
    }

    public void save(ApplicationScoringModelDTO dto) {
        save(dto, true);
    }

    public void update(ApplicationScoringModelDTO dto) {
        save(dto, false);
    }

    @Transactional
    public void saveAll(List<ApplicationScoringModelDTO> list) {
        list.forEach(this::save);
    }

    @Transactional
    public void updateAll(List<ApplicationScoringModelDTO> list) { list.forEach(this::update); }

    public void delete(ApplicationScoringModelDTO dto) {
        delete(dto.getScoringModelName(), dto.getCodRecordStatus(), dto.getValidFrom());
    }

    public void delete(String type, String status, LocalDate date) {
        service.delete(repository, prepareId(type, status, date));
    }

    private ApplicationScoringModelEmbeddedKey prepareId(String scoringModel, String status, LocalDate date) {
        return ApplicationScoringModelEmbeddedKey.builder().codScoringModel(scoringModel).codRecordStatus(status).validFrom(date).build();
    }

    private void save(ApplicationScoringModelDTO dto, boolean isSave) {
        ApplicationScoringModelEmbeddedKey id = prepareId(dto.getCodScoringModel(), dto.getCodRecordStatus(), dto.getValidFrom());
        ApplicationScoringModelMaster entity = isSave ?
                service.beforeSave(repository, mapper, ApplicationScoringModelMaster.class, id, dto) :
                service.beforeUpdate(repository, mapper, ApplicationScoringModelMaster.class, id, dto);
        entity.setId(id);
        service.save(repository, entity);
    }

    @PostConstruct
    void postConstruct() {
        TypeMap<ApplicationScoringModelMaster, ApplicationScoringModelDTO> dtoMap = mapper.createTypeMap(ApplicationScoringModelMaster.class, ApplicationScoringModelDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodScoringModel(), ApplicationScoringModelDTO::setCodScoringModel);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), ApplicationScoringModelDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getValidFrom(), ApplicationScoringModelDTO::setValidFrom);
        dtoMap.addMapping(MakerChecker::getLastMakerId, ApplicationScoringModelDTO::setLastMakerId);
        dtoMap.addMapping(MakerChecker::getLastMakerDateTime, ApplicationScoringModelDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerChecker::getLastCheckerId, ApplicationScoringModelDTO::setLastCheckerId);
        dtoMap.addMapping(MakerChecker::getLastCheckerDateTime, ApplicationScoringModelDTO::setLastCheckerDateTime);

        TypeMap<ApplicationScoringModelDTO, ApplicationScoringModelMaster> entityMap = mapper.createTypeMap(ApplicationScoringModelDTO.class, ApplicationScoringModelMaster.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, ApplicationScoringModelMaster::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, ApplicationScoringModelMaster::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, ApplicationScoringModelMaster::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, ApplicationScoringModelMaster::setLastCheckerDateTime);
    }

}
