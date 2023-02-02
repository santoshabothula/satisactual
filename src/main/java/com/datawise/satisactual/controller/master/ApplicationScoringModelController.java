package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.ApplicationScoringModelEmbeddedKey;
import com.datawise.satisactual.entities.master.ApplicationScoringModelEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.ApplicationScoringModelDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.ApplicationScoringModelRepository;
import com.datawise.satisactual.service.master.CommonMasterService;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Validated
@RestController
@RequestMapping("/mst/application-scoring-models")
@CrossOrigin
public class ApplicationScoringModelController {

    @Autowired
    private CommonMasterService<ApplicationScoringModelEntity, ApplicationScoringModelEmbeddedKey, ApplicationScoringModelDTO> service;
    @Autowired
    private ApplicationScoringModelRepository repository;

    private final ModelMapper mapper = new ModelMapper();
    private final String primaryKey = "codAddressType";

    @GetMapping
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<List<ApplicationScoringModelDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, ApplicationScoringModelDTO.class));
    }

    @GetMapping("/{id}/{date}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<ApplicationScoringModelDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id,
            @Valid @NotNull @DateTimeFormat(pattern="yyyy-MM-dd") @JsonFormat(pattern="yyyy-MM-dd") @PathVariable("date") LocalDate date
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(id, CodRecordStatus.A, date), mapper, ApplicationScoringModelDTO.class));
    }

    @PostMapping("/save")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody ApplicationScoringModelDTO dto) {
        service.save(
                repository,
                dto,
                ApplicationScoringModelEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodScoringModel(), dto.getValidFrom()),
                dto.getCodScoringModel() + ";" + dto.getValidFrom(),
                mapper,
                this.id(dto.getCodScoringModel(), CodRecordStatus.N, dto.getValidFrom()),
                this.id(dto.getCodScoringModel(), CodRecordStatus.A, dto.getValidFrom())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody ApplicationScoringModelDTO dto) {
        service.update(
                repository,
                dto,
                ApplicationScoringModelEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodScoringModel(), dto.getValidFrom()),
                dto.getCodScoringModel() + ";" + dto.getValidFrom(),
                mapper,
                this.id(dto.getCodScoringModel(), CodRecordStatus.M, dto.getValidFrom()),
                this.id(dto.getCodScoringModel(), CodRecordStatus.A, dto.getValidFrom())
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}/{date}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id,
            @Valid @NotNull @DateTimeFormat(pattern="yyyy-MM-dd") @JsonFormat(pattern="yyyy-MM-dd") @PathVariable("date") LocalDate date
    ) {
        service.delete(
                repository,
                ApplicationScoringModelDTO.class,
                ApplicationScoringModelEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id, date),
                id + ";" + date,
                mapper,
                this.id(id, CodRecordStatus.X, date),
                this.id(id, CodRecordStatus.C, date),
                this.id(id, CodRecordStatus.A, date)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{id}/{date}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id,
            @Valid @NotNull @DateTimeFormat(pattern="yyyy-MM-dd") @JsonFormat(pattern="yyyy-MM-dd") @PathVariable("date") LocalDate date
    ) {
        service.reopen(
                repository,
                ApplicationScoringModelDTO.class,
                ApplicationScoringModelEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id, date),
                id + ";" + date,
                mapper,
                this.id(id, CodRecordStatus.R, date),
                this.id(id, CodRecordStatus.A, date),
                this.id(id, CodRecordStatus.C, date)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{id}/{date}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id,
            @Valid @NotNull @DateTimeFormat(pattern="yyyy-MM-dd") @JsonFormat(pattern="yyyy-MM-dd") @PathVariable("date") LocalDate date
    ) {
        service.authorize(
                repository,
                ApplicationScoringModelDTO.class,
                ApplicationScoringModelEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), id, date),
                id,
                mapper,
                this.id(id, CodRecordStatus.A, date),
                this.id(id, CodRecordStatus.C, date)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<ApplicationScoringModelEntity, ApplicationScoringModelDTO> dtoMap = mapper.createTypeMap(ApplicationScoringModelEntity.class, ApplicationScoringModelDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodScoringModel(), ApplicationScoringModelDTO::setCodScoringModel);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), ApplicationScoringModelDTO::setCodRecordStatus);
        dtoMap.addMapping(c -> c.getId().getValidFrom(), ApplicationScoringModelDTO::setValidFrom);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, ApplicationScoringModelDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, ApplicationScoringModelDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, ApplicationScoringModelDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, ApplicationScoringModelDTO::setLastCheckerDateTime);

        TypeMap<ApplicationScoringModelDTO, ApplicationScoringModelEntity> entityMap = mapper.createTypeMap(ApplicationScoringModelDTO.class, ApplicationScoringModelEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, ApplicationScoringModelEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, ApplicationScoringModelEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, ApplicationScoringModelEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, ApplicationScoringModelEntity::setLastCheckerDateTime);
    }

    private ApplicationScoringModelEmbeddedKey id(String code, CodRecordStatus status, LocalDate date) {
        return ApplicationScoringModelEmbeddedKey.builder().codScoringModel(code).codRecordStatus(status.name()).validFrom(date).build();
    }

    private Specification<ApplicationScoringModelEntity> getSpec(List<CodRecordStatus> statuses, String code, LocalDate date) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(code, "codScoringModel"))
                .and(repository.findRecordWithCode(date, "validFrom"));
    }
}
