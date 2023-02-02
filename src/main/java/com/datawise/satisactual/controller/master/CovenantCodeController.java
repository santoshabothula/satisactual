package com.datawise.satisactual.controller.master;

import com.datawise.satisactual.entities.master.CovenantCodeEmbeddedKey;
import com.datawise.satisactual.entities.master.CovenantCodeEntity;
import com.datawise.satisactual.entities.master.MakerCheckerEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.master.dto.CovenantCodeDTO;
import com.datawise.satisactual.model.master.dto.CustomResponse;
import com.datawise.satisactual.model.master.dto.MakerCheckerDTO;
import com.datawise.satisactual.repository.master.CovenantCodeRepository;
import com.datawise.satisactual.service.master.CommonMasterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
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
@RequestMapping("/mst/covenant-code")
@CrossOrigin
public class CovenantCodeController {

    @Autowired
    private CommonMasterService<CovenantCodeEntity, CovenantCodeEmbeddedKey, CovenantCodeDTO> service;
    @Autowired
    private CovenantCodeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<List<CovenantCodeDTO>> getActiveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActiveAll(repository, mapper, CovenantCodeDTO.class));
    }

    @GetMapping("/{id}/{date}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CovenantCodeDTO> get(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(repository, this.id(id, CodRecordStatus.A), mapper, CovenantCodeDTO.class));
    }

    @PostMapping("/save")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody CovenantCodeDTO dto) {
        service.save(
                repository,
                dto,
                CovenantCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCovenant()),
                dto.getCodCovenant(),
                mapper,
                this.id(dto.getCodCovenant(), CodRecordStatus.N),
                this.id(dto.getCodCovenant(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Save operation completed successfully").build());
    }

    @PutMapping("/update")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> update(@Valid @RequestBody CovenantCodeDTO dto) {
        service.update(
                repository,
                dto,
                CovenantCodeEntity.class,
                getSpec(Arrays.asList(CodRecordStatus.values()), dto.getCodCovenant()),
                dto.getCodCovenant(),
                mapper,
                this.id(dto.getCodCovenant(), CodRecordStatus.M),
                this.id(dto.getCodCovenant(), CodRecordStatus.A)
        );
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.builder().message("Update operation completed successfully").build());
    }

    @DeleteMapping("/delete/{id}/{date}")
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> delete(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id
    ) {
        service.delete(
                repository,
                CovenantCodeDTO.class,
                CovenantCodeEntity.class,
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
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> reopen(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id
    ) {
        service.reopen(
                repository,
                CovenantCodeDTO.class,
                CovenantCodeEntity.class,
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
    @ApiOperation(value = "Master Table", authorizations = {@Authorization(value="basicAuth")})
    public ResponseEntity<CustomResponse> authorize(
            @Valid @NotBlank @Size(min = 1, max = 4) @PathVariable("id") String id
    ) {
        service.authorize(
                repository,
                CovenantCodeDTO.class,
                CovenantCodeEntity.class,
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
        TypeMap<CovenantCodeEntity, CovenantCodeDTO> dtoMap = mapper.createTypeMap(CovenantCodeEntity.class, CovenantCodeDTO.class);
        dtoMap.addMapping(c -> c.getId().getCodCovenant(), CovenantCodeDTO::setCodCovenant);
        dtoMap.addMapping(c -> c.getId().getCodRecordStatus(), CovenantCodeDTO::setCodRecordStatus);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerId, CovenantCodeDTO::setLastMakerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastMakerDateTime, CovenantCodeDTO::setLastMakerDateTime);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerId, CovenantCodeDTO::setLastCheckerId);
        dtoMap.addMapping(MakerCheckerEntity::getLastCheckerDateTime, CovenantCodeDTO::setLastCheckerDateTime);

        TypeMap<CovenantCodeDTO, CovenantCodeEntity> entityMap = mapper.createTypeMap(CovenantCodeDTO.class, CovenantCodeEntity.class);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerId, CovenantCodeEntity::setLastMakerId);
        entityMap.addMapping(MakerCheckerDTO::getLastMakerDateTime, CovenantCodeEntity::setLastMakerDateTime);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerId, CovenantCodeEntity::setLastCheckerId);
        entityMap.addMapping(MakerCheckerDTO::getLastCheckerDateTime, CovenantCodeEntity::setLastCheckerDateTime);
    }

    private CovenantCodeEmbeddedKey id(String code, CodRecordStatus status) {
        return CovenantCodeEmbeddedKey.builder().codCovenant(code).codRecordStatus(status.name()).build();
    }

    private Specification<CovenantCodeEntity> getSpec(List<CodRecordStatus> statuses, String code) {
        return repository.findRecordWithStatusIn(statuses)
                .and(repository.findRecordWithCode(code, "codCovenant"));
    }
}
