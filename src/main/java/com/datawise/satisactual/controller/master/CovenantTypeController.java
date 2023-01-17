package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.CovenantTypeEmbeddedKey;
import com.datawise.satisactual.entities.master.CovenantTypeEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CovenantTypeDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.CovenantTypeRepository;
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

@Validated
@RestController
@RequestMapping("/mst/covenant-type")
public class CovenantTypeController {

    @Autowired
    private CommonMasterService<CovenantTypeEntity, CovenantTypeEmbeddedKey, CovenantTypeDTO> service;
    @Autowired
    private CovenantTypeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<CovenantTypeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, CovenantTypeDTO.class));
    }

    @GetMapping("/{id}/{date}")
    public ResponseEntity<CovenantTypeDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(id, CodRecordStatus.A), mapper, CovenantTypeDTO.class));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody CovenantTypeDTO dto) {
        service.save(
                repository,
                dto,
                CovenantTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCovenantType()),
                dto.getCodCovenantType(),
                mapper,
                this.id(dto.getCodCovenantType(), CodRecordStatus.N),
                this.id(dto.getCodCovenantType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody CovenantTypeDTO dto) {
        service.update(
                repository,
                dto,
                CovenantTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCovenantType()),
                dto.getCodCovenantType(),
                mapper,
                this.id(dto.getCodCovenantType(), CodRecordStatus.M),
                this.id(dto.getCodCovenantType(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}/{date}")
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id
    ) {
        service.delete(
                repository,
                CovenantTypeDTO.class,
                CovenantTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id),
                id,
                mapper,
                this.id(id, CodRecordStatus.X),
                this.id(id, CodRecordStatus.C),
                this.id(id, CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Delete operation completed successfully").build());
    }

    @PostMapping("/reopen/{id}/{date}")
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id
    ) {
        service.reopen(
                repository,
                CovenantTypeDTO.class,
                CovenantTypeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), id),
                id,
                mapper,
                this.id(id, CodRecordStatus.R),
                this.id(id, CodRecordStatus.A),
                this.id(id, CodRecordStatus.C)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Reopen operation completed successfully").build());
    }

    @PostMapping("/authorize/{id}")
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id
    ) {
        service.authorize(
                repository,
                CovenantTypeDTO.class,
                CovenantTypeEntity.class,
                getSpec(List.of(CodRecordStatus.N, CodRecordStatus.M, CodRecordStatus.X, CodRecordStatus.R), id),
                id,
                mapper,
                this.id(id, CodRecordStatus.A),
                this.id(id, CodRecordStatus.C)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Authorize request completed successfully").build());
    }

    @PostConstruct
    private void postConstruct() {
        TypeMap<CovenantTypeEntity, CovenantTypeDTO> dtoMap = mapper.createTypeMap(CovenantTypeEntity.class, CovenantTypeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodCovenantType(), CovenantTypeDTO::setCodCovenantType);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), CovenantTypeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, CovenantTypeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, CovenantTypeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, CovenantTypeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, CovenantTypeDTO::setLastCheckerDateTime);

        TypeMap<CovenantTypeDTO, CovenantTypeEntity> entityMap = mapper.createTypeMap(CovenantTypeDTO.class, CovenantTypeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, CovenantTypeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, CovenantTypeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, CovenantTypeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, CovenantTypeEntity::setLastCheckerDateTime);
    }

    private CovenantTypeEmbeddedKey id(String code, CodRecordStatus status) {
        return CovenantTypeEmbeddedKey.builder().codCovenantType(code).codRecordStatus(status.name()).build();
    }

    private Specification<CovenantTypeEntity> getSpec(List<CodRecordStatus> statuses, String code) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(code, "codCovenantType"));
    }
}
