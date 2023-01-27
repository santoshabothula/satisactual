package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.entities.master.TrainingTemplateModuleEmbeddedKey;
import com.datawise.satisactual.entities.master.TrainingTemplateModuleEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.model.master.dto.TrainingTemplateModuleDTO;
import com.datawise.satisactual.repository.master.TrainingTemplateModuleRepository;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;

@Validated
@RestController
@RequestMapping("/mst/training-template-module")
public class TrainingTemplateModuleController {

    @Autowired
    private CommonMasterService<TrainingTemplateModuleEntity, TrainingTemplateModuleEmbeddedKey, TrainingTemplateModuleDTO> service;
    @Autowired
    private TrainingTemplateModuleRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<TrainingTemplateModuleDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, TrainingTemplateModuleDTO.class));
    }

    @GetMapping("/{training-type}/{training-day}/{num-day-session}")
    public ResponseEntity<TrainingTemplateModuleDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("training-type") String trainingType,
            @Valid @NotNull @PathVariable("training-day") Integer trainingDay,
            @Valid @NotNull @PathVariable("num-day-session") Integer numDaySession
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(CodRecordStatus.A, trainingType, trainingDay, numDaySession), mapper, TrainingTemplateModuleDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody TrainingTemplateModuleDTO dto) {
        service.save(
                repository,
                dto,
                TrainingTemplateModuleEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodTrainingType(), dto.getTrainingDay(), dto.getDaySession()),
                dto.getCodTrainingType() + ";" + dto.getTrainingDay() + ";" + dto.getDaySession(),
                mapper,
                this.id(CodRecordStatus.N, dto.getCodTrainingType(), dto.getTrainingDay(), dto.getDaySession()),
                this.id(CodRecordStatus.A, dto.getCodTrainingType(), dto.getTrainingDay(), dto.getDaySession())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody TrainingTemplateModuleDTO dto) {
        service.update(
                repository,
                dto,
                TrainingTemplateModuleEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodTrainingType(), dto.getTrainingDay(), dto.getDaySession()),
                dto.getCodTrainingType() + ";" + dto.getTrainingDay() + ";" + dto.getDaySession(),
                mapper,
                this.id(CodRecordStatus.M, dto.getCodTrainingType(), dto.getTrainingDay(), dto.getDaySession()),
                this.id(CodRecordStatus.A, dto.getCodTrainingType(), dto.getTrainingDay(), dto.getDaySession())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{training-type}/{training-day}/{num-day-session}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("training-type") String trainingType,
            @Valid @NotNull @PathVariable("training-day") Integer trainingDay,
            @Valid @NotNull @PathVariable("num-day-session") Integer numDaySession
    ) {
        service.delete(
                repository,
                TrainingTemplateModuleDTO.class,
                TrainingTemplateModuleEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), trainingType, trainingDay, numDaySession),
                trainingType + ";" + trainingDay + ";" + numDaySession,
                mapper,
                this.id(CodRecordStatus.X, trainingType, trainingDay, numDaySession),
                this.id(CodRecordStatus.C, trainingType, trainingDay, numDaySession),
                this.id(CodRecordStatus.A, trainingType, trainingDay, numDaySession)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{training-type}/{training-day}/{num-day-session}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("training-type") String trainingType,
            @Valid @NotNull @PathVariable("training-day") Integer trainingDay,
            @Valid @NotNull @PathVariable("num-day-session") Integer numDaySession
    ) {
        service.reopen(
                repository,
                TrainingTemplateModuleDTO.class,
                TrainingTemplateModuleEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), trainingType, trainingDay, numDaySession),
                trainingType + ";" + trainingDay + ";" + numDaySession,
                mapper,
                this.id(CodRecordStatus.R, trainingType, trainingDay, numDaySession),
                this.id(CodRecordStatus.A, trainingType, trainingDay, numDaySession),
                this.id(CodRecordStatus.C, trainingType, trainingDay, numDaySession)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{training-type}/{training-day}/{num-day-session}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("training-type") String trainingType,
            @Valid @NotNull @PathVariable("training-day") Integer trainingDay,
            @Valid @NotNull @PathVariable("num-day-session") Integer numDaySession
    ) {
        service.authorize(
                repository,
                TrainingTemplateModuleDTO.class,
                TrainingTemplateModuleEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), trainingType, trainingDay, numDaySession),
                trainingType + ";" + trainingDay + ";" + numDaySession,
                mapper,
                this.id(CodRecordStatus.A, trainingType, trainingDay, numDaySession),
                this.id(CodRecordStatus.C, trainingType, trainingDay, numDaySession)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<TrainingTemplateModuleEntity, TrainingTemplateModuleDTO> dtoMap = mapper.createTypeMap(TrainingTemplateModuleEntity.class, TrainingTemplateModuleDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodTrainingType(), TrainingTemplateModuleDTO::setCodTrainingType);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), TrainingTemplateModuleDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getTrainingDay(), TrainingTemplateModuleDTO::setTrainingDay);
        dtoMap.addMapping(c -> c.getId().getDaySession(), TrainingTemplateModuleDTO::setDaySession);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, TrainingTemplateModuleDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, TrainingTemplateModuleDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, TrainingTemplateModuleDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, TrainingTemplateModuleDTO::setLastCheckerDateTime);

        TypeMap<TrainingTemplateModuleDTO, TrainingTemplateModuleEntity> entityMap = mapper.createTypeMap(TrainingTemplateModuleDTO.class, TrainingTemplateModuleEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, TrainingTemplateModuleEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, TrainingTemplateModuleEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, TrainingTemplateModuleEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, TrainingTemplateModuleEntity::setLastCheckerDateTime);
    }

    private TrainingTemplateModuleEmbeddedKey id(CodRecordStatus status, String trainingType, Integer trainingDay, Integer numDaySession) {
        return TrainingTemplateModuleEmbeddedKey.builder().codTrainingType(trainingType).codRecordStatus(status.name()).trainingDay(trainingDay).daySession(numDaySession).build();
    }

    private Specification<TrainingTemplateModuleEntity> getSpec(List<CodRecordStatus> statuses, String trainingType, Integer trainingDay, Integer numDaySession) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(trainingType, "codTrainingType"))
                .and(repository.findRecordWithCode(trainingDay, "trainingDay"))
                .and(repository.findRecordWithCode(numDaySession, "daySession"));
    }
}
