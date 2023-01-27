package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.TrainingTemplateEmbeddedKey;
import com.datawise.satisactual.entities.master.TrainingTemplateEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.TrainingTemplateDTO;
import com.datawise.satisactual.repository.master.TrainingTemplateRepository;
import com.datawise.satisactual.service.master.CommonMasterService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

@Validated
@RestController
@RequestMapping("/mst/training-template")
public class TrainingTemplateController {

    @Autowired
    private CommonMasterService<TrainingTemplateEntity, TrainingTemplateEmbeddedKey, TrainingTemplateDTO> service;
    @Autowired
    private TrainingTemplateRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<TrainingTemplateDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, TrainingTemplateDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingTemplateDTO> get(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id.apply(id, CodRecordStatus.A), mapper, TrainingTemplateDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody TrainingTemplateDTO dto) {
        service.save(
                repository,
                dto,
                TrainingTemplateEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodTrainingType()),
                dto.getCodTrainingType(),
                mapper,
                this.id.apply(dto.getCodTrainingType(), CodRecordStatus.N),
                this.id.apply(dto.getCodTrainingType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody TrainingTemplateDTO dto) {
        service.update(
                repository,
                dto,
                TrainingTemplateEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodTrainingType()),
                dto.getCodTrainingType(),
                mapper,
                this.id.apply(dto.getCodTrainingType(), CodRecordStatus.M),
                this.id.apply(dto.getCodTrainingType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.delete(
                repository,
                TrainingTemplateDTO.class,
                TrainingTemplateEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id),
                id,
                mapper,
                this.id.apply(id, CodRecordStatus.X),
                this.id.apply(id, CodRecordStatus.C),
                this.id.apply(id, CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{id}")
    public ResponseEntity<CustomResponse> reopen(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.reopen(
                repository,
                TrainingTemplateDTO.class,
                TrainingTemplateEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id),
                id,
                mapper,
                this.id.apply(id, CodRecordStatus.R),
                this.id.apply(id, CodRecordStatus.A),
                this.id.apply(id, CodRecordStatus.C)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{id}")
    public ResponseEntity<CustomResponse> authorize(@Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id) {
        service.authorize(
                repository,
                TrainingTemplateDTO.class,
                TrainingTemplateEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.R, CodRecordStatus.X, CodRecordStatus.M, CodRecordStatus.N), id),
                id,
                mapper,
                this.id.apply(id, CodRecordStatus.A),
                this.id.apply(id, CodRecordStatus.C)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<TrainingTemplateEntity, TrainingTemplateDTO> dtoMap = mapper.createTypeMap(TrainingTemplateEntity.class, TrainingTemplateDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodTrainingType(), TrainingTemplateDTO::setCodTrainingType);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), TrainingTemplateDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, TrainingTemplateDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, TrainingTemplateDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, TrainingTemplateDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, TrainingTemplateDTO::setLastCheckerDateTime);

        TypeMap<TrainingTemplateDTO, TrainingTemplateEntity> entityMap = mapper.createTypeMap(TrainingTemplateDTO.class, TrainingTemplateEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, TrainingTemplateEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, TrainingTemplateEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, TrainingTemplateEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, TrainingTemplateEntity::setLastCheckerDateTime);
    }

    private final BiFunction<String, CodRecordStatus, TrainingTemplateEmbeddedKey> id = (code, status) -> TrainingTemplateEmbeddedKey.builder().codTrainingType(code).codRecordStatus(status.name()).build();

    private Specification<TrainingTemplateEntity> getSpec(List<CodRecordStatus> statuses, String id) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(id, "codTrainingType"));
    }
}
